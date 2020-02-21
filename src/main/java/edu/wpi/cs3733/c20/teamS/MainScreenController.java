package edu.wpi.cs3733.c20.teamS;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinding;
import edu.wpi.cs3733.c20.teamS.widgets.AutoComplete;
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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class MainScreenController implements Initializable {
    //region fields
    private Stage stage;
    private IPathfinding algorithm;
    private Group pathGroup = new Group();
    private PathDisplay tester;
    private boolean flip = true;
    private MapZoomer zoomer;
    private FloorSelector floorSelector;
    private MutableGraph<NodeData> graph;
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

            this.current = floorNumber;
            updateFloorButtons(floorNumber);
            updateMapPanPosition(floorNumber);
        }

        private void updateFloorButtons(int floorNumber) {
            for (Floor floor : this.floors)
                floor.button.setStyle(UNSELECTED_BUTTON_STYLE);

            floor(floorNumber).button.setStyle(SELECTED_BUTTON_STYLE);
            this.upButton.setDisable(floorNumber == highestFloorNumber);
            this.downButton.setDisable(floorNumber == lowestFloorNumber);
        }
        private void updateMapPanPosition(int floorNumber) {
            double currentHval = scrollPane.getHvalue();
            double currentVval = scrollPane.getVvalue();
            mapImage.setImage(floor(floorNumber).image);
            zoomer.zoomSet();
            if (tester.getCounter() >= 0)
                tester.pathDraw(this.current);
            populateCollidersForCurrentFloor();
            keepCurrentPosition(currentHval, currentVval, zoomer);
        }
        private Floor floor(int floorNumber) {
            return floors[floorNumber - 1];
        }
    }

    public MainScreenController(Stage stage, IPathfinding algorithm){
        this.algorithm = algorithm;
        this.stage = stage;
        tester = new PathDisplay(pathGroup, parentVBox, this.algorithm);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initSearchComboBoxFont();
        initSearchComboBoxAutoComplete();

        zoomer = new MapZoomer(mapImage, scrollPane);
        tester = new PathDisplay(pathGroup, parentVBox, algorithm);
        initFloorSelector();

        initGraph();
    }

    private void initGraph() {
        graph = GraphBuilder.undirected().allowsSelfLoops(true).build();
        DatabaseController database = new DatabaseController();
        Map<String, NodeData> nodeIdMap = database.getAllNodes().stream()
                .collect(Collectors.toMap(node -> node.getNodeID(), node -> node));
        Set<EdgeData> edges = database.getAllEdges();
        for (NodeData node : nodeIdMap.values())
            graph.addNode(node);
        for (EdgeData edge : edges) {
            NodeData start = nodeIdMap.get(edge.getStartNode());
            NodeData end = nodeIdMap.get(edge.getEndNode());
            graph.putEdge(start, end);
        }
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
        floorSelector.setCurrent(3);
    }
    private void initSearchComboBoxFont() {
        String fontFamily = searchComboBox.getEditor().getFont().getFamily();
        Font font = new Font(fontFamily, 18);
        searchComboBox.getEditor().setFont(font);
    }
    private void initSearchComboBoxAutoComplete() {
        DatabaseController db = new DatabaseController();
        Set<NodeData> nodes = db.getAllNodes();
        List<String> dictionary = nodes.stream()
                .map(node -> node.getLongName() + ", " + node.getNodeID())
                .collect(toList());
        AutoComplete.start(dictionary, searchComboBox);
    }

    public void populateCollidersForCurrentFloor() {
        Group group = new Group();
        group.getChildren().clear();
        group.getChildren().add(mapImage);
        group.setOnMouseClicked(e -> this.tester.setNode(updateNearestNodeLabels(e.getX(), e.getY())));

        this.tester.pathDraw(floorSelector.current());
        group.getChildren().add(pathGroup);
        scrollPane.setContent(group);
    }

    private NodeData updateNearestNodeLabels(double x, double y) {
        NodeData nearest = findNearestNodeWithin(x, y, 200);

        if (flip) {
            location1.setText(nearest.getLongName());
            flip = false;
        } else if (!flip) {
            location2.setText(nearest.getLongName());
            flip = true;
        }
        return nearest;
    }

    private NodeData findNearestNodeWithin(double x, double y, double radius) {
        List<NodeData> sorted = graph.nodes().stream()
                    .filter(node -> node.getFloor() == floorSelector.current())
                    .filter(node -> distance(x, y, node) < radius)
                    .sorted((a, b) -> Double.compare(
                            distance(x, y, a),
                            distance(x, y, b)
                    ))
                    .collect(Collectors.toList());
        if (sorted.isEmpty())
            return null;
        return sorted.get(0);
    }

    private double distance(double x1, double y1, double x2, double y2) {
        double xSquared = x1 - x2;
        xSquared *= xSquared;
        double ySquared = y1 - y2;
        ySquared *= ySquared;

        return Math.sqrt(xSquared + ySquared);
    }
    private double distance(double x, double y, NodeData node) {
        return distance(x, y, node.getxCoordinate(), node.getyCoordinate());
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
    @FXML private VBox parentVBox;
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/TutorialScreen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Help");
            window.setScene(new Scene(root1));
            window.setResizable(false);
            window.show();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }
    @FXML private void onStaffClicked() {
        LoginScreen.showDialog(this.stage);
    }
    @FXML private void onPathfindClicked() {
        double currentHval = scrollPane.getHvalue();
        double currentVval = scrollPane.getVvalue();
        populateCollidersForCurrentFloor();
        keepCurrentPosition(currentHval, currentVval, zoomer);
    }
    @FXML private void onSwapButtonPressed() {
        String temp = location2.getText();
        location2.setText(location1.getText());
        location1.setText(temp);
    }
    @FXML private void onZoomInClicked() {
        this.zoomer.zoomIn();
        if (zoomer.getZoomStage() == 3){
            zoomInButton.setDisable(true);
        }
        else{
            zoomOutButton.setDisable(false);
            zoomInButton.setDisable(false);
        }
    }
    @FXML private void onZoomOutClicked() {
        //Node content = scrollPane.getContent();
        this.zoomer.zoomOut();
        if (zoomer.getZoomStage() == -2){
            zoomOutButton.setDisable(true);
        }
        else{
            zoomOutButton.setDisable(false);
            zoomInButton.setDisable(false);
        }
    }
    //endregion
}