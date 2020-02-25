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
import edu.wpi.cs3733.c20.teamS.widgets.LookupResult;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

public class MainScreenController implements Initializable {
    //region fields
    private Stage stage;
    private IPathfinder pathfinder;
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

    public MainScreenController(Stage stage, IPathfinder pathfinder){
        if (stage == null) ThrowHelper.illegalNull("stage");
        if (pathfinder == null) ThrowHelper.illegalNull("pathfinder");

        this.stage = stage;
        this.pathfinder = pathfinder;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSearchComboBox();

        zoomer = new MapZoomer(scrollPane);
        initGraph();
        renderer = new PathRenderer();
        nodeSelector = new SelectNodesStateMachine(graph, pathfinder);

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
    private void initSearchComboBox() {
        String fontFamily = searchComboBox.getEditor().getFont().getFamily();
        Font font = new Font(fontFamily, 18);
        searchComboBox.getEditor().setFont(font);

        DatabaseController db = new DatabaseController();
        Set<NodeData> nodes = db.getAllNodes();
        AutoComplete.start(nodes, searchComboBox, NodeData::getLongName);
        searchComboBox.valueProperty().addListener((sender, previous, current) -> {
            floorSelector.setCurrent(current.value().getFloor());
            onNodeClicked(current.value());
        });
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

    private void onNodeClicked(NodeData node) {
        if (node == null)
            return;

        if (flip) {
            location1.setText(node.getLongName());
            flip = false;
        } else if (!flip) {
            location2.setText(node.getLongName());
            flip = true;
        }

        nodeSelector.onNodeSelected(node);
    }
    private void onMapClicked(MouseEvent e) {
        final double x = e.getX();
        final double y = e.getY();
        NodeData nearest = findNearestNodeWithin(x, y, 200);
        onNodeClicked(nearest);
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
    @FXML private VBox directoryVBox;
    @FXML private JFXButton zoomInButton;
    @FXML private JFXButton zoomOutButton;
    @FXML private Label location2;
    @FXML private ComboBox<LookupResult<NodeData>> searchComboBox;
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
    @FXML private void onAboutClicked() {
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
        zoomOutButton.setDisable(!zoomer.canZoomOut());
    }
    @FXML private void onZoomOutClicked() {
        //Node content = scrollPane.getContent();
        this.zoomer.zoomOut();
        zoomOutButton.setDisable(!zoomer.canZoomOut());
        zoomInButton.setDisable(!zoomer.canZoomIn());
    }
    //endregion
}