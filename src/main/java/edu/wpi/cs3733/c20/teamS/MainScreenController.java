package edu.wpi.cs3733.c20.teamS;

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

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.*;

import java.util.stream.Collectors;

public class MainScreenController implements Initializable {
    private Stage stage;
    private IPathfinding algorithm;
    private Group group2 = new Group();
    private PathDisplay tester2;
    private boolean flip = true;
    private MapZoomer zoomer;
    private FloorSelector floorSelector;

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
            if (tester2.getCounter() >= 0)
                tester2.pathDraw(this.current);
            drawNodesEdges();
            keepCurrentPosition(currentHval, currentVval, zoomer);
        }
        private Floor floor(int floorNumber) {
            return floors[floorNumber - 1];
        }
    }

    public MainScreenController(Stage stage, IPathfinding algorithm){
        this.algorithm = algorithm;
        this.stage = stage;
        tester2 = new PathDisplay(group2, parentVBox, this.algorithm);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initSearchComboBoxFont();
        initSearchComboBoxAutoComplete();

        zoomer = new MapZoomer(mapImage, scrollPane);
        tester2 = new PathDisplay(group2, parentVBox, algorithm);
        initFloorSelector();
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
                .collect(Collectors.toList());
        AutoComplete.start(dictionary, searchComboBox);
    }

    public void drawNodesEdges() {
        String floor = "0" + floorSelector.current();
        Group group = new Group();
        group.getChildren().clear();
        group.getChildren().add(mapImage);
        PathDisplay tester = new PathDisplay(group, parentVBox, this.algorithm);
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodes();
        group.setOnMouseClicked(e -> tester2.setNode(findNearestNode(e.getX(), e.getY())));
        for (NodeData data : nd) {
            Circle circle1 = new Circle(data.getxCoordinate(), data.getyCoordinate(), 0);
            circle1.setStroke(Color.ORANGE);
            circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
            if (data.getNodeType().equals("ELEV")) {
                circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.5));
            }
            circle1.setOnMouseClicked(e -> tester2.setNode(data));
            circle1.setVisible(false);
            if (data.getNodeID().substring(data.getNodeID().length() - 2).equals(floor)) {
                circle1.setVisible(true);
            }
            group.getChildren().add(circle1);
        }
        Set<EdgeData> ed = dbc.getAllEdges();
        for (EdgeData data : ed) {
            if (data.getEdgeID().substring(data.getEdgeID().length() - 2).equals(floor)) {
                int startX = 0;
                int startY = 0;
                int endX = 0;
                int endY = 0;
                boolean checker1 = false;
                boolean checker2 = false;
                for (NodeData check : nd) {
                    String start = "Start Location";
                    if (check.getNodeID().equals(start)) {
                        checker1 = true;
                        startX = (int) check.getxCoordinate();
                        startY = (int) check.getyCoordinate();
                    }
                    String end = "End Location";
                    if (check.getNodeID().equals(end)) {
                        checker2 = true;
                        endX = (int) check.getxCoordinate();
                        endY = (int) check.getyCoordinate();
                    }
                }
                if (checker1 && checker2) {
                    Line line1 = new Line();
                    line1.setStartX(startX);
                    line1.setStartY(startY);
                    line1.setEndX(endX);
                    line1.setEndY(endY);
                    line1.setStroke(Color.BLUE);
                    line1.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
                    line1.setStrokeWidth(0);
                    line1.setVisible(false);
                    if (data.getEdgeID().substring(data.getEdgeID().length() - 2).equals(floor)) {
                        line1.setVisible(true);
                    }
                    group.getChildren().add(line1);
                }
            }
        }
        tester2.pathDraw(floorSelector.current());
        group.getChildren().add(group2);
        scrollPane.setContent(group);
    }
    private NodeData findNearestNode(double x, double y) {
        NodeData nearest = new NodeData();
        double distance = 200;

        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodes();

        for (NodeData temp : nd) {
            if (temp.getFloor() == floorSelector.current()) {
                if (Math.sqrt(Math.pow((x - temp.getxCoordinate()), 2) + Math.pow((y - temp.getyCoordinate()), 2)) < distance) {
                    distance = Math.sqrt(Math.pow((x - temp.getxCoordinate()), 2) + Math.pow((y - temp.getyCoordinate()), 2));
                    nearest = temp;
                }
            }
        }
        if (flip) {
            location1.setText(nearest.getLongName());
            flip = false;
        } else if (!flip) {
            location2.setText(nearest.getLongName());
            flip = true;
        }
        return nearest;
    }

    private void keepCurrentPosition(double Hval, double Vval, MapZoomer zoomer){
        zoomer.zoomSet();
        scrollPane.setHvalue(Hval);
        scrollPane.setVvalue(Vval);
    }

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
        drawNodesEdges();
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
}