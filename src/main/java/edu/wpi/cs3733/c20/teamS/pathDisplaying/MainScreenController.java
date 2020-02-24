package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.google.common.graph.MutableGraph;
import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.LoginScreen;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Hitbox;
import edu.wpi.cs3733.c20.teamS.collisionMasks.HitboxRepository;
import edu.wpi.cs3733.c20.teamS.collisionMasks.ResourceFolderHitboxRepository;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinder;
import edu.wpi.cs3733.c20.teamS.utilities.Numerics;
import edu.wpi.cs3733.c20.teamS.widgets.AutoComplete;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class MainScreenController implements Initializable {
    //region fields
    private Stage stage;
    private IPathfinder algorithm;
    private PathRenderer renderer;
    private SelectNodesStateMachine nodeSelector;
    private MapZoomer zoomer;
    private FloorSelector floorSelector;
    private MutableGraph<NodeData> graph;
    private final Group group = new Group();
    private final HitboxRepository hitboxRepo = new ResourceFolderHitboxRepository();
    private final Set<Hitbox> hitboxes = new HashSet<>();

    private boolean flip = true;
    //endregion

    private static class Floor {
        public final Image image;
        public final JFXButton button;

        public Floor(JFXButton button, Image image) {
            this.image = image;
            this.button = button;
        }
        public Floor(JFXButton button, String imagePath) {
            this(button, new Image(imagePath));
        }
    }
    private class FloorSelector {
        private final JFXButton upButton;
        private final JFXButton downButton;
        private final Floor[] floors;
        private final PublishSubject<Integer> currentChanged = PublishSubject.create();
        private int current;
        private static final String UNSELECTED_BUTTON_STYLE = "-fx-background-color: #ffffff; -fx-font: 22 System;";
        private static final String SELECTED_BUTTON_STYLE = "-fx-background-color: #f6bd38; -fx-font: 32 System;";
        private final int lowestFloorNumber = 1;
        private final int highestFloorNumber;

        public FloorSelector(JFXButton upButton, JFXButton downButton, Floor... floors) {
            this.upButton = upButton;
            this.downButton = downButton;
            this.floors = floors;
            this.highestFloorNumber = floors.length;
        }

        /**
         * Gets the currently-selected floor number.
         * @return
         */
        public int current() {
            return this.current;
        }
        /**
         * Sets the currently-selected floor number. Floor numbers start at 1.
         * @param floorNumber The floor number to select.
         */
        public void setCurrent(int floorNumber) {
            if (floorNumber < lowestFloorNumber || floorNumber > highestFloorNumber)
                ThrowHelper.outOfRange("floorNumber", lowestFloorNumber, highestFloorNumber);
            int previous = this.current;
            this.current = floorNumber;
            updateFloorButtons(floorNumber);
//            updateMapPanPosition(floorNumber);
            if (previous != this.current)
                currentChanged.onNext(this.current);
        }
        public Observable<Integer> currentChanged() {
            return currentChanged;
        }

        private void updateFloorButtons(int floorNumber) {
            for (Floor floor : this.floors)
                floor.button.setStyle(UNSELECTED_BUTTON_STYLE);

            floor(floorNumber).button.setStyle(SELECTED_BUTTON_STYLE);
            this.upButton.setDisable(floorNumber == highestFloorNumber);
            this.downButton.setDisable(floorNumber == lowestFloorNumber);
        }
        public Floor floor(int floorNumber) {
            return floors[floorNumber - 1];
        }
    }

    public MainScreenController(Stage stage, IPathfinder algorithm){
        this.algorithm = algorithm;
        this.stage = stage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSearchComboBoxFont();

        zoomer = new MapZoomer(scrollPane);
        initGraph();
        renderer = new PathRenderer();
        nodeSelector = new SelectNodesStateMachine(graph, algorithm);

        initFloorSelector();

        nodeSelector.pathChanged().subscribe(path -> {
            redraw();
            renderer.printInstructions(path, instructionVBox);
        });
        group.setOnMouseClicked(this::onMapClicked);
        scrollPane.setContent(group);

        initHitboxes();

        redraw();
    }

    private void initHitboxes() {
        hitboxes.addAll(hitboxRepo.load());
    }
    private void initGraph() {
        DatabaseController database = new DatabaseController();
        graph = database.loadGraph();
    }
    private void initFloorSelector() {
        floorSelector = new FloorSelector(
                upButton, downButton,
                new Floor(floorButton1, "images/Floors/HospitalFloor1.png"),
                new Floor(floorButton2, "images/Floors/HospitalFloor2.png"),
                new Floor(floorButton3, "images/Floors/HospitalFloor3.png"),
                new Floor(floorButton4, "images/Floors/HospitalFloor4.png"),
                new Floor(floorButton5, "images/Floors/HospitalFloor5.png")
        );
        floorSelector.setCurrent(2);
        floorSelector.currentChanged().subscribe(e -> redraw());
    }
    private void initSearchComboBoxFont() {
        String fontFamily = searchComboBox.getEditor().getFont().getFamily();
        Font font = new Font(fontFamily, 18);
        searchComboBox.getEditor().setFont(font);

        DatabaseController db = new DatabaseController();
        Set<NodeData> nodes = db.getAllNodes();
        List<String> dictionary = nodes.stream()
                .map(node -> node.getLongName() + ", " + node.getNodeID())
                .collect(toList());
        AutoComplete.start(dictionary, searchComboBox);
    }

    private void redraw() {
        double currentHval = scrollPane.getHvalue();
        double currentVval = scrollPane.getVvalue();

        mapImage.setImage(floorSelector.floor(floorSelector.current()).image);
        zoomer.zoomSet();

        group.getChildren().clear();
        group.getChildren().add(mapImage);

        Group pathGroup = renderer.draw(nodeSelector.path(), floorSelector.current());
        group.getChildren().add(pathGroup);

        hitboxes.stream()
                .filter(hitbox -> hitbox.floor() == floorSelector.current())
                .map(this::createHitboxRenderingMask)
                .forEach(polygon -> group.getChildren().add(polygon));

        keepCurrentPosition(currentHval, currentVval, zoomer);
    }

    private void onMapClicked(MouseEvent e) {
        final double x = e.getX();
        final double y = e.getY();
        NodeData nearest = findNearestNodeWithin(x, y, 200);
        if (nearest == null)
            return;

        if (flip) {
            location1.setText(nearest.getLongName());
            flip = false;
        } else if (!flip) {
            location2.setText(nearest.getLongName());
            flip = true;
        }

        nodeSelector.onNodeClicked(nearest);
        //pathDrawer.setNode(nearest);
    }

    private Polygon createHitboxRenderingMask(Hitbox hitbox) {
        Color visible = Color.AQUA.deriveColor(1, 1, 1, 0.5);
        Color invisible = Color.AQUA.deriveColor(1, 1, 1, 0);
        Polygon polygon = hitbox.toPolygon();
        polygon.setFill(invisible);
        polygon.setOnMouseEntered(e -> polygon.setFill(visible));
        polygon.setOnMouseExited(e -> polygon.setFill(invisible));

        return polygon;
    }
    private NodeData findNearestNodeWithin(double x, double y, double radius) {
        List<NodeData> sorted = graph.nodes().stream()
                    .filter(node -> node.getFloor() == floorSelector.current())
                    .filter(node -> Numerics.distance(x, y, node.getxCoordinate(), node.getyCoordinate()) < radius)
                    .sorted((a, b) -> Double.compare(
                            Numerics.distance(x, y, a.getxCoordinate(), a.getyCoordinate()),
                            Numerics.distance(x, y, b.getxCoordinate(), b.getyCoordinate())
                    ))
                    .collect(Collectors.toList());
        if (sorted.isEmpty())
            return null;
        return sorted.get(0);
    }

    private void keepCurrentPosition(double Hval, double Vval, MapZoomer zoomer){
        zoomer.zoomSet();
        scrollPane.setHvalue(Hval);
        scrollPane.setVvalue(Vval);
    }

    //region ui widgets
    @FXML private ImageView mapImage;
    @FXML private ScrollPane scrollPane;
    @FXML private JFXButton floorButton1;
    @FXML private JFXButton floorButton2;
    @FXML private JFXButton floorButton3;
    @FXML private JFXButton floorButton4;
    @FXML private JFXButton floorButton5;
    @FXML private JFXButton downButton;
    @FXML private JFXButton upButton;
    @FXML private Label location1;
    @FXML private VBox instructionVBox;
    @FXML private JFXButton zoomInButton;
    @FXML private JFXButton zoomOutButton;
    @FXML private Label location2;
    @FXML private ComboBox<String> searchComboBox;
    //endregion

    //region event handlers
    @FXML private void onUpClicked() {
        floorSelector.setCurrent(floorSelector.current() + 1);
    }
    @FXML private void onDownClicked() {
        floorSelector.setCurrent(floorSelector.current() - 1);
    }
    @FXML private void onFloorClicked1() {
        floorSelector.setCurrent(1);
    }
    @FXML private void onFloorClicked2() {
        floorSelector.setCurrent(2);
    }
    @FXML private void onFloorClicked3() {
        floorSelector.setCurrent(3);
    }
    @FXML private void onFloorClicked4() {
        floorSelector.setCurrent(4);
    }
    @FXML private void onFloorClicked5() {
        floorSelector.setCurrent(5);
    }
    @FXML private void onHelpClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/AboutMe.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            //Parent  root1 = fxmlLoader.getRoot();
            Stage window = new Stage();
           window.initModality(Modality.WINDOW_MODAL);
           window.setFullScreen(false);
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();
        } catch (IOException e) {
            System.out.println("Can't load new window");
        }
    }

    @FXML private void onStaffClicked() {
        LoginScreen.showDialog(this.stage);
    }

    @FXML private void onSwapButtonPressed() {
        String temp = location2.getText();
        location2.setText(location1.getText());
        location1.setText(temp);
    }
    @FXML private void onZoomInClicked() {
        zoomer.zoomIn();
        zoomInButton.setDisable(!zoomer.canZoomIn());
    }
    @FXML private void onZoomOutClicked() {
        //Node content = scrollPane.getContent();
        this.zoomer.zoomOut();
        zoomOutButton.setDisable(!zoomer.canZoomOut());
    }
    //endregion
}