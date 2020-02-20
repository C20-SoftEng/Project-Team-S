package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinding;
import edu.wpi.cs3733.c20.teamS.widgets.AutoComplete;
import javafx.event.ActionEvent;
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
import java.util.List;
import java.util.LinkedList;
import java.util.ResourceBundle;

import java.util.Set;
import java.util.stream.Collectors;

public class MainScreenController implements Initializable {

    private Stage stage;
    private IPathfinding algorithm;
    private int currentFloor = 2;
    private MapZoomer zoomer;
    private LinkedList<String> instructions;
    private double currentHval;
    private double currentVval;
    private Image floor1 = new Image("images/Floors/HospitalFloor1.png");
    private Image floor2 = new Image("images/Floors/HospitalFloor2.png");
    private Image floor3 = new Image("images/Floors/HospitalFloor3.png");
    private Image floor4 = new Image("images/Floors/HospitalFloor4.png");
    private Image floor5 = new Image("images/Floors/HospitalFloor5.png");
    private Group group2 = new Group();
    private PathDisplay tester2;
    private boolean flip = true;

    public MainScreenController(Stage stage, IPathfinding algorithm){
        this.algorithm = algorithm;
        this.stage = stage;
        tester2 = new PathDisplay(group2, parentVBox, this.algorithm);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomer = new MapZoomer(mapImage, scrollPane);

        initSearchComboBoxFont();
        initSearchComboBoxAutoComplete();

            zoomer = new MapZoomer(mapImage, scrollPane);

            tester2 = new PathDisplay(group2, parentVBox, algorithm);
            instructions = new LinkedList<String>();
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

    @FXML private ImageView mapImage;
    @FXML private ScrollPane scrollPane;
    @FXML private JFXButton floorButton1;
    @FXML private JFXButton floorButton2;
    @FXML private JFXButton floorButton3;
    @FXML private JFXButton floorButton4;
    @FXML private JFXButton floorButton5;
    @FXML private JFXButton downButton;
    @FXML private JFXButton upButton;
    @FXML private JFXButton pathfindButton;
    @FXML private JFXButton swapButton;
    @FXML private Label location1;
    @FXML private VBox parentVBox;

    @FXML private JFXButton zoomInButton;
    @FXML private JFXButton zoomOutButton;

    @FXML private Label location2;
    @FXML private ComboBox<String> searchComboBox;

    @FXML private void onUpClicked(ActionEvent event) {
        currentHval = scrollPane.getHvalue();
        currentVval = scrollPane.getVvalue();
        currentFloor += 1;
        if (currentFloor == 1) {
            set1();
            mapImage.setImage(floor1);
            currentFloor = 1;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(currentFloor);
            }
            drawNodesEdges();
        } else if (currentFloor == 2) {
            set2();
            mapImage.setImage(floor2);
            currentFloor = 2;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(currentFloor);
            }
            drawNodesEdges();
        } else if (currentFloor == 3) {
            set3();
            mapImage.setImage(floor3);
            currentFloor = 3;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(currentFloor);
            }
            drawNodesEdges();
        } else if (currentFloor == 4) {
            set4();
            mapImage.setImage(floor4);
            currentFloor = 4;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(currentFloor);
            }
            drawNodesEdges();
        } else if (currentFloor == 5) {
            set5();
            mapImage.setImage(floor5);
            currentFloor = 5;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(currentFloor);
            }
            drawNodesEdges();
        }
        keepCurrentPosition(currentHval, currentVval, zoomer);
    }

    @FXML private void onDownClicked(ActionEvent event) {
        currentHval = scrollPane.getHvalue();
        currentVval = scrollPane.getVvalue();
        currentFloor -= 1;
        if (currentFloor == 1) {
            set1();
            mapImage.setImage(floor1);
            currentFloor = 1;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(currentFloor);
            }
            drawNodesEdges();
        }
        if (currentFloor == 2) {
            set2();
            currentFloor = 2;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(currentFloor);
            }
            mapImage.setImage(floor2);
            drawNodesEdges();

        }
        if (currentFloor == 3) {
            set3();
            mapImage.setImage(floor3);
            currentFloor = 3;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(currentFloor);
            }
            drawNodesEdges();
        }
        if (currentFloor == 4) {
            set4();
            mapImage.setImage(floor4);
            currentFloor = 4;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(currentFloor);
            }
            drawNodesEdges();
        }
        if (currentFloor == 5) {
            set5();
            mapImage.setImage(floor5);
            currentFloor = 5;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(currentFloor);
            }
            drawNodesEdges();
        }
        keepCurrentPosition(currentHval, currentVval, zoomer);
    }

    private void set1() {
        floorButton1.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        upButton.setDisable(false);
        downButton.setDisable(true);
    }
    private void set2() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        mapImage.setImage(floor2);
        upButton.setDisable(false);
        downButton.setDisable(false);
    }
    private void set3() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
    }
    private void set4() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        upButton.setDisable(false);
        downButton.setDisable(false);
    }
    private void set5() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        upButton.setDisable(true);
        downButton.setDisable(false);
    }

    @FXML private void onFloorClicked1(ActionEvent event) {
        currentHval = scrollPane.getHvalue();
        currentVval = scrollPane.getVvalue();
        set1();
        mapImage.setImage(floor1);
        currentFloor = 1;
        this.zoomer.zoomSet();
        if (tester2.getCounter() >= 2) {
            tester2.pathDraw(currentFloor);
        }
        drawNodesEdges();
        keepCurrentPosition(currentHval, currentVval, zoomer);
    }

    @FXML private void onFloorClicked2() {
        //location1.setText(start);
        //location2.setText(end);
        currentHval = scrollPane.getHvalue();
        currentVval = scrollPane.getVvalue();
        set2();
        currentFloor = 2;
        this.zoomer.zoomSet();
        if (tester2.getCounter() >= 2) {
            tester2.pathDraw(currentFloor);
        }
        mapImage.setImage(floor2);
        drawNodesEdges();
        keepCurrentPosition(currentHval, currentVval, zoomer);

    }
    @FXML private void onFloorClicked3() {
        currentHval = scrollPane.getHvalue();
        currentVval = scrollPane.getVvalue();
        set3();
        mapImage.setImage(floor3);
        currentFloor = 3;
        this.zoomer.zoomSet();
        upButton.setDisable(false);
        downButton.setDisable(false);
        if (tester2.getCounter() >= 2) {
            tester2.pathDraw(currentFloor);
        }
        drawNodesEdges();
        keepCurrentPosition(currentHval, currentVval, zoomer);

    }
    @FXML private void onFloorClicked4() {
        currentHval = scrollPane.getHvalue();
        currentVval = scrollPane.getVvalue();
        set4();
        mapImage.setImage(floor4);
        currentFloor = 4;
        this.zoomer.zoomSet();
        if (tester2.getCounter() >= 2) {
            tester2.pathDraw(currentFloor);
        }
        drawNodesEdges();
        keepCurrentPosition(currentHval, currentVval, zoomer);


        }
    @FXML private void onFloorClicked5() {
        currentHval = scrollPane.getHvalue();
        currentVval = scrollPane.getVvalue();
        set5();
        mapImage.setImage(floor5);
        currentFloor = 5;
        this.zoomer.zoomSet();
        if (tester2.getCounter() >= 2) {
            tester2.pathDraw(currentFloor);
        }
        drawNodesEdges();
        keepCurrentPosition(currentHval, currentVval, zoomer);

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

    private void keepCurrentPosition(double Hval, double Vval, MapZoomer zoomer){
        zoomer.zoomSet();
        scrollPane.setHvalue(Hval);
        scrollPane.setVvalue(Vval);
    }

    public JFXButton getFloor2() {
        return floorButton2;
    }
    public void drawNodesEdges() {

        String floor = "0" + currentFloor;

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

        tester2.pathDraw(currentFloor);

        group.getChildren().add(group2);

       /*Set<NodeData> ball = dbc.getAllNodesOfType("ELEV");

        for (NodeData data : ball) {
                ImageView elev = new ImageView();
                elev.setImage(new Image("images/Balloons/elevator.png"));
                elev.setX(data.getxCoordinate() - 20);
                elev.setY(data.getyCoordinate() - 40);
                elev.setPreserveRatio(true);
                elev.setFitWidth(40);
                group.getChildren().add(elev);
        }

        ball = dbc.getAllNodesOfType("REST");

           for(NodeData data : ball) {
               ImageView elev = new ImageView();
               elev.setImage(new Image("images/Balloons/bathroom.png"));
               elev.setX(data.getxCoordinate() - 20);
               elev.setY(data.getyCoordinate() - 40);
               elev.setPreserveRatio(true);
               elev.setFitWidth(40);
               group.getChildren().add(elev);
           }

        ball = dbc.getAllNodesOfType("STAI");
          for(NodeData data: ball) {
                ImageView elev = new ImageView();
                elev.setImage(new Image("images/Balloons/staris.png"));
                elev.setX(data.getxCoordinate() - 20);
                elev.setY(data.getyCoordinate() - 40);
                elev.setPreserveRatio(true);
                elev.setFitWidth(40);
                group.getChildren().add(elev);
            }*/


        scrollPane.setContent(group);
    }

    private NodeData findNearestNode(double x, double y) {
        NodeData nearest = new NodeData();
        double distance = 200;

        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodes();

        for (NodeData temp : nd) {
            if (temp.getFloor() == currentFloor) {
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
}