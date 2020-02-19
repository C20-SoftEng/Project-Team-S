package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinding;
import edu.wpi.cs3733.c20.teamS.pathfinding.Path;
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

public class mainScreenController implements Initializable {

    private Stage stage;
    private IPathfinding algorithm;
    private WrittenInstructions writtenInstructions;

    public mainScreenController(Stage mainStage, IPathfinding pathAlgorithm){
        this.algorithm = pathAlgorithm;
        this.stage = mainStage;
        tester2 = new PathDisplay(group2, this.algorithm);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomer = new MapZoomer(mapImage, scrollPane);

        initSearchComboBoxFont();
        initSearchComboBoxAutoComplete();
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

    int current_floor = 2;
    String newFloor;
    private MapZoomer zoomer;
    private LinkedList<String> instructions;
    Image floor1 = new Image("images/Floors/HospitalFloor1.png");
    Image floor2 = new Image("images/Floors/HospitalFloor2.png");
    Image floor3 = new Image("images/Floors/HospitalFloor3.png");
    Image floor4 = new Image("images/Floors/HospitalFloor4.png");
    Image floor5 = new Image("images/Floors/HospitalFloor5.png");
    Group group2 = new Group();
    PathDisplay tester2;

    private boolean flip = true;

    //@FXML JFXButton elevatorButton;

    @FXML
    private ImageView mapImage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXButton floorButton1;
    @FXML
    private JFXButton floorButton2;
    @FXML
    private JFXButton floorButton3;
    @FXML
    private JFXButton floorButton4;
    @FXML
    private JFXButton floorButton5;
    @FXML
    private JFXButton downButton;
    @FXML
    private JFXButton upButton;
    @FXML
    private JFXButton pathfindButton;
    @FXML
    private JFXButton swapButton;
    @FXML
    private Label location1;
    @FXML
    private VBox parentVBox;


    @FXML
    private Label location2;
    @FXML
    private ComboBox<String> searchComboBox;
    private String start = "Start Location";
    private String end = "End Location";

    @FXML
    void onUpClicked(ActionEvent event) {
        current_floor += 1;
        if (current_floor == 1) {
            set1();
            mapImage.setImage(floor1);
            current_floor = 1;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(current_floor);
            }
            drawNodesEdges();
        } else if (current_floor == 2) {
            set2();
            mapImage.setImage(floor2);
            current_floor = 2;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(current_floor);
            }
            drawNodesEdges();
        } else if (current_floor == 3) {
            set3();
            mapImage.setImage(floor3);
            current_floor = 3;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(current_floor);
            }
            drawNodesEdges();
        } else if (current_floor == 4) {
            set4();
            mapImage.setImage(floor4);
            current_floor = 4;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(current_floor);
            }
            drawNodesEdges();
        } else if (current_floor == 5) {
            set5();
            mapImage.setImage(floor5);
            current_floor = 5;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(current_floor);
            }
            drawNodesEdges();
        }
    }

    @FXML
    void onDownClicked(ActionEvent event) {
        current_floor -= 1;
        ;
        if (current_floor == 1) {
            set1();
            mapImage.setImage(floor1);
            current_floor = 1;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(current_floor);
            }
            drawNodesEdges();
        }
        if (current_floor == 2) {
            set2();
            current_floor = 2;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(current_floor);
            }
            mapImage.setImage(floor2);
            drawNodesEdges();

        }
        if (current_floor == 3) {
            set3();
            mapImage.setImage(floor3);
            current_floor = 3;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(current_floor);
            }
            drawNodesEdges();
        }
        if (current_floor == 4) {
            set4();
            mapImage.setImage(floor4);
            current_floor = 4;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(current_floor);
            }
            drawNodesEdges();
        }
        if (current_floor == 5) {
            set5();
            mapImage.setImage(floor5);
            current_floor = 5;
            if (tester2.getCounter() >= 2) {
                tester2.pathDraw(current_floor);
            }
            drawNodesEdges();
        }
    }

    void set1() {
        floorButton1.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        upButton.setDisable(false);
        downButton.setDisable(true);
    }

    void set2() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        mapImage.setImage(floor2);
        upButton.setDisable(false);
        downButton.setDisable(false);
    }

    void set3() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
    }

    void set4() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        upButton.setDisable(false);
        downButton.setDisable(false);
    }

    void set5() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        upButton.setDisable(true);
        downButton.setDisable(false);
    }

    @FXML
    void onFloorClicked1(ActionEvent event) {
        set1();
        mapImage.setImage(floor1);
        current_floor = 1;
        this.zoomer.zoomSet();
        if (tester2.getCounter() >= 2) {
            tester2.pathDraw(current_floor);
        }
        drawNodesEdges();
    }

    @FXML
    void onFloorClicked2(ActionEvent event) {
        //location1.setText(start);
        //location2.setText(end);
        set2();
        current_floor = 2;
        this.zoomer.zoomSet();
        if (tester2.getCounter() >= 2) {
            tester2.pathDraw(current_floor);
        }
        mapImage.setImage(floor2);
        drawNodesEdges();
    }

    @FXML
    void onFloorClicked3(ActionEvent event) {
        set3();
        mapImage.setImage(floor3);
        current_floor = 3;
        this.zoomer.zoomSet();
        upButton.setDisable(false);
        downButton.setDisable(false);
        if (tester2.getCounter() >= 2) {
            tester2.pathDraw(current_floor);
        }
        drawNodesEdges();
    }

    @FXML
    void onFloorClicked4(ActionEvent event) {
        set4();
        mapImage.setImage(floor4);
        current_floor = 4;
        this.zoomer.zoomSet();
        if (tester2.getCounter() >= 2) {
            tester2.pathDraw(current_floor);
        }
        drawNodesEdges();

    }

    @FXML
    void onFloorClicked5(ActionEvent event) {
        set5();
        mapImage.setImage(floor5);
        current_floor = 5;
        this.zoomer.zoomSet();
        if (tester2.getCounter() >= 2) {
            tester2.pathDraw(current_floor);
        }
        drawNodesEdges();
    }

    @FXML
    void onHelpClicked(ActionEvent event) {
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

    @FXML
    void onStaffClicked(ActionEvent event) {
        LoginScreen.showDialog(this.stage);
    }

    private void keepCurrentPosition(double Hval, double Vval, MapZoomer zoomer){
        zoomer.zoomSet();
        scrollPane.setHvalue(Hval);
        scrollPane.setVvalue(Vval);
    }

    @FXML
    void onPathfindClicked(ActionEvent event) {
        double currentHval = scrollPane.getHvalue();
        double currentVval = scrollPane.getVvalue();
        drawNodesEdges();
        keepCurrentPosition(currentHval, currentVval, zoomer);
    }

    @FXML
    void onSwapButtonPressed(ActionEvent event) {
        String temp = location2.getText();
        location2.setText(location1.getText());
        location1.setText(temp);
    }

    @FXML
    void onZoomInClicked(ActionEvent event) {
        this.zoomer.zoomIn();
    }

    @FXML
    void onZoomOutClicked(ActionEvent event) {
        //Node content = scrollPane.getContent();
        this.zoomer.zoomOut();
    }

    public JFXButton getFloor2() {
        return floorButton2;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomer = new MapZoomer(mapImage, scrollPane);

        tester2 = new PathDisplay(group2, parentVBox);
        instructions = new LinkedList<String>();
    }

    public void drawNodesEdges() {

        String floor = "0" + current_floor;

        Group group = new Group();

        group.getChildren().clear();

        group.getChildren().add(mapImage);

        PathDisplay tester = new PathDisplay(group, this.algorithm, parentVBox);

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
                    if (check.getNodeID().equals(start)) {
                        checker1 = true;
                        startX = (int) check.getxCoordinate();
                        startY = (int) check.getyCoordinate();
                    }
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

        tester2.pathDraw(current_floor);

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
            if (temp.getFloor() == current_floor) {
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