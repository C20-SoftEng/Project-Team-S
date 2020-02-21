package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.MapZoomer;

import edu.wpi.cs3733.c20.teamS.app.serviceRequests.ActiveServiceRequestScreen;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.*;

import edu.wpi.cs3733.c20.teamS.pathfinding.AStar;
import edu.wpi.cs3733.c20.teamS.pathfinding.BreadthFirst;
import edu.wpi.cs3733.c20.teamS.pathfinding.DepthFirst;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinding;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.SelectServiceScreen;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.MainToLoginScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class EditScreenController implements Initializable {
    //region fields
    private Stage stage;
    private boolean btwn = false;
    private Employee loggedIn;
    //private int current_floor = 2;
    private double currentHval;
    private double currentVval;
    private Group group2 = new Group();
    private MapEditingTasks tester2 = new MapEditingTasks(group2);
    private MoveNodes moveNode = new MoveNodes();
    private MapZoomer zoomer;
    private FloorSelector floorSelector;
    //endregion

    /**
     *
     * @param stage the stage to take over
     * @param employee the employee that logged in
     */
    public EditScreenController(Stage stage, Employee employee) {
        this.stage  = stage;
        this.loggedIn = employee;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomer = new MapZoomer(scrollPane);
        loggedInUserLabel.setText("Welcome " + loggedIn.name() + "!");
        editPrivilegeBox.setVisible(loggedIn.accessLevel() == AccessLevel.ADMIN);

        initFloorSelector();
        floorSelector.setCurrent(2);
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
    }

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
        private static final String SELECTED_BUTTON_STYLE = "-fx-background-color: #f6bd38; -fx-font: 32 System;";
        private static final String UNSELECTED_BUTTON_STYLE = "-fx-background-color: #ffffff; -fx-font: 22 System;";
        private final Floor[] floors_;
        private final JFXButton upButton;
        private final JFXButton downButton;
        private int current;
        private final int lowestFloor;
        private final int highestFloor;

        public FloorSelector(JFXButton upButton, JFXButton downButton, Floor... floors) {
            this.upButton = upButton;
            this.downButton = downButton;
            this.floors_ = floors;
            lowestFloor = 1;
            highestFloor = floors_.length;
        }

        public int current() {
            return current;
        }
        public void setCurrent(int floorNumber) {
            for (Floor floor : floors_) {
                floor.button.setStyle(UNSELECTED_BUTTON_STYLE);
            }
            floor(floorNumber).button.setStyle(SELECTED_BUTTON_STYLE);
            this.upButton.setDisable(floorNumber == this.highestFloor);
            this.downButton.setDisable(floorNumber == this.lowestFloor);

            mapImage.setImage(floor(floorNumber).image);
            this.current = floorNumber;
            drawNodesEdges();
        }

        private Floor floor(int floorNumber) {
            return floors_[floorNumber - 1];
        }
    }

    //region gui components
    @FXML private JFXRadioButton addNodeRadio;
    @FXML private JFXRadioButton removeNodeRadio;
    @FXML private JFXRadioButton addEdgeRadio;
    @FXML private JFXRadioButton removeEdgeRadio;
    @FXML private JFXRadioButton moveNodeRadio;
    @FXML private JFXRadioButton showInfoRadio;
    @FXML private VBox editPrivilegeBox;
    @FXML private Label loggedInUserLabel;
    @FXML private ImageView mapImage;
    @FXML private ScrollPane scrollPane;
    @FXML private JFXButton floorButton1;
    @FXML private JFXButton floorButton2;
    @FXML private JFXButton floorButton3;
    @FXML private JFXButton floorButton4;
    @FXML private JFXButton floorButton5;
    @FXML private JFXButton downButton;
    @FXML private JFXButton upButton;
    @FXML private ToggleGroup pathGroup;
    @FXML private JFXButton zoomInButton;
    @FXML private JFXButton zoomOutButton;
    @FXML private JFXButton cancelEditsButton;
    @FXML private JFXButton confirmEditButton;
    //endregion

    //region event handlers
    @FXML void onUpClicked() {
        floorSelector.setCurrent(floorSelector.current() + 1);
    }
    @FXML void onDownClicked() {
        floorSelector.setCurrent(floorSelector.current() - 1);
    }
    @FXML void onFloorClicked1() {
        floorSelector.setCurrent(1);
    }
    @FXML void onFloorClicked2() {
        floorSelector.setCurrent(2);
    }
    @FXML void onFloorClicked3() {
        floorSelector.setCurrent(3);
    }
    @FXML void onFloorClicked4() {
        floorSelector.setCurrent(4);

    }
    @FXML void onFloorClicked5() {
        floorSelector.setCurrent(5);
    }
    @FXML void onHelpClicked() {
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
    @FXML void onStaffClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/loginScreen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Login to the System");
            window.setScene(new Scene(root1));
            window.setResizable(false);
            window.show();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }
    @FXML void onZoomInClicked() {
        this.zoomer.zoomIn();
        if (zoomer.getZoomStage() == 3) {
            zoomInButton.setDisable(true);
            zoomOutButton.setDisable(false);
        } else {
            zoomOutButton.setDisable(false);
            zoomOutButton.setDisable(false);
        }
    }
    @FXML void onZoomOutClicked() {
        this.zoomer.zoomOut();
        if (zoomer.getZoomStage() == -2) {
            zoomOutButton.setDisable(true);
            zoomInButton.setDisable(false);
        } else {
            zoomOutButton.setDisable(false);
            zoomInButton.setDisable(false);
        }
    }
    @FXML void onNewServiceClicked(ActionEvent event) {
        SelectServiceScreen.showDialog(loggedIn);
    }
    @FXML void onActiveServiceClicked() {
        ObservableList<ServiceData> setOfActives = FXCollections.observableArrayList();
        DatabaseController dbc = new DatabaseController();
        Set<ServiceData> dbData = dbc.getAllServiceRequestData();
        for(ServiceData sd : dbData){
            if(!(sd.getStatus().equals("COMPLETE"))){
                setOfActives.add(sd);
                System.out.println(sd.toString());
            }
        }
        ActiveServiceRequestScreen.showDialog(setOfActives);
    }
    //endregion

    public JFXButton getFloorButton2() {return floorButton2;}
    public void drawNodesEdges() {
        currentHval = scrollPane.getHvalue();
        currentVval = scrollPane.getVvalue();
        unselectALL();
        moveNode.setScale(zoomer.zoomFactor());
        moveNode.setCurrent_floor(floorSelector.current());

        String floor = "0" + floorSelector.current();
        Group group = new Group();
        group.getChildren().clear();
        group.getChildren().add(mapImage);

        MapEditingTasks tester = new MapEditingTasks(group);
        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodes();

        for (NodeData data : nd) {
            Circle circle1 = new Circle(data.getxCoordinate(), data.getyCoordinate(), 25);
            circle1.setStroke(Color.ORANGE);
            circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
            if (data.getNodeType().equals("ELEV")) {
                circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.5));
            }
            circle1.setVisible(false);
            if (data.getNodeID().substring(data.getNodeID().length() - 2).equals(floor)) {
                circle1.setVisible(true);
            }
            group.getChildren().add(circle1);
        }

        Set<EdgeData> ed = dbc.getAllEdges();

        for (EdgeData data : ed) {
            if(data.getEdgeID().substring(data.getEdgeID().length()-2).equals(floor)) {
                String start = data.getStartNode();
                String end = data.getEndNode();
                int startX = 0;
                int startY = 0;
                int endX = 0;
                int endY = 0;
                boolean checker1 = false;
                boolean checker2 = false;
                for(NodeData check: nd) {
                    if(check.getNodeID().equals(start)) {
                        checker1 = true;
                        startX = (int)check.getxCoordinate();
                        startY = (int)check.getyCoordinate();
                    }
                    if(check.getNodeID().equals(end)) {
                        checker2 = true;
                        endX = (int)check.getxCoordinate();
                        endY = (int)check.getyCoordinate();
                    }
                }
                if(checker1 && checker2) {
                    Line line1 = new Line();
                    line1.setStartX(startX);
                    line1.setStartY(startY);
                    line1.setEndX(endX);
                    line1.setEndY(endY);
                    line1.setStroke(Color.BLUE);
                    line1.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
                    line1.setStrokeWidth(5);
                    line1.setVisible(false);
                    if (data.getEdgeID().substring(data.getEdgeID().length() - 2).equals(floor)) {
                        line1.setVisible(true);
                    }
                    group.getChildren().add(line1);
                }
            }
        }

        radioButtonEventHandlers_fromDrawNodesEdges(tester);

        group.getChildren().add(group2);
        scrollPane.setContent(group);

        //Keeps the zoom the same throughout each screen/floor change.
        keepCurrentPosition(currentHval, currentVval, zoomer);
        if(btwn) {
            addEdgeRadio.fire();
        }
    }

    private void radioButtonEventHandlers_fromDrawNodesEdges(MapEditingTasks tester) {
        moveNodeRadio.setOnAction(e -> {
            btwn = false;
            currentHval = scrollPane.getHvalue();
            currentVval = scrollPane.getVvalue();
            tester.moveNodes(mapImage, floorSelector.current(), moveNode);
            keepCurrentPosition(currentHval, currentVval, zoomer);
        });
        showInfoRadio.setOnAction(e -> {
            btwn = false;
            currentHval = scrollPane.getHvalue();
            currentVval = scrollPane.getVvalue();
            tester.showNodeInfo(mapImage, floorSelector.current());
            keepCurrentPosition(currentHval, currentVval, zoomer);
        });
        addNodeRadio.setOnAction(e -> {
            btwn = false;
            currentHval = scrollPane.getHvalue();
            currentVval = scrollPane.getVvalue();
            tester.drawNodes(floorSelector.current());
            keepCurrentPosition(currentHval, currentVval, zoomer);
        });
        removeNodeRadio.setOnAction(e -> {
            btwn = false;
            currentHval = scrollPane.getHvalue();
            currentVval = scrollPane.getVvalue();
            tester.removeNodes(mapImage, floorSelector.current());
            keepCurrentPosition(currentHval, currentVval, zoomer);

        });
        addEdgeRadio.setOnAction(e -> {
            btwn = true;
            currentHval = scrollPane.getHvalue();
            currentVval = scrollPane.getVvalue();
            tester2.addEdge(mapImage, floorSelector.current());
            keepCurrentPosition(currentHval, currentVval, zoomer);
        });
        removeEdgeRadio.setOnAction(e -> {
            btwn = false;
            currentHval = scrollPane.getHvalue();
            currentVval = scrollPane.getVvalue();
            tester.removeEdge(mapImage, floorSelector.current());
            keepCurrentPosition(currentHval, currentVval, zoomer);
        });

        confirmEditButton.setOnAction(e -> {
            tester.saveChanges();
        });
        cancelEditsButton.setOnAction(e -> {
            tester.cancelChanges();
            new MapEditingScreen(stage, loggedIn);
        });
    }

    public void onLogOut() {
        IPathfinding pathfinder = new AStar();
        switch(((RadioButton)pathGroup.getSelectedToggle()).getText()){
            case "A*":
                pathfinder = new AStar();
                break;
            case "BreadthFirst":
                pathfinder = new BreadthFirst();
                break;
            case "DepthFirst":
                pathfinder = new DepthFirst();
                break;
        }
        MainToLoginScreen back = new MainToLoginScreen(stage, pathfinder);
    }
    private void unselectALL() {
        addNodeRadio.selectedProperty().set(false);
        removeNodeRadio.selectedProperty().set(false);
        removeEdgeRadio.selectedProperty().set(false);
        addEdgeRadio.selectedProperty().set(false);
        moveNodeRadio.selectedProperty().set(false);
        showInfoRadio.selectedProperty().set(false);
    }
    private void keepCurrentPosition(double Hval, double Vval, MapZoomer zoomer){
        zoomer.zoomSet();
        scrollPane.setHvalue(Hval);
        scrollPane.setVvalue(Vval);
    }
}
