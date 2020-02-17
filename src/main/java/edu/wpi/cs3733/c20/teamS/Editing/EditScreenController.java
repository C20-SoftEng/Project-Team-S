package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.c20.teamS.MapZoomer;
import edu.wpi.cs3733.c20.teamS.PathDisplay;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class EditScreenController implements Initializable {
        int current_floor = 2;
        String newFloor;
        private MapZoomer zoomer;
        Image floor1 = new Image("images/Floors/HospitalFloor1.png");
        Image floor2 = new Image("images/Floors/HospitalFloor2.png");
        Image floor3 = new Image("images/Floors/HospitalFloor3.png");
        Image floor4 = new Image("images/Floors/HospitalFloor4.png");
        Image floor5 = new Image("images/Floors/HospitalFloor5.png");
        @FXML JFXRadioButton addNodeRadio;
        @FXML JFXRadioButton removeNodeRadio;
        @FXML JFXRadioButton addEdgeRadio;
        @FXML JFXRadioButton removeEdgeRadio;

        @FXML private ImageView mapImage;

        @FXML private ScrollPane scrollPane;

        @FXML private JFXButton floorButton1;
        @FXML private JFXButton floorButton2;
        @FXML private JFXButton floorButton3;
        @FXML private JFXButton floorButton4;
        @FXML private JFXButton floorButton5;

        public JFXButton getFloorButton2() {return floorButton2;}

        private void neurtalizeButtons() {
            floorButton1.setPrefWidth(44);
            floorButton1.setPrefWidth(50);
            floorButton1.setStyle("-fx-background-color:  #fff");
            floorButton2.setPrefWidth(44);
            floorButton2.setPrefWidth(50);
            floorButton2.setStyle("-fx-background-color:  #fff");
            floorButton3.setPrefWidth(44);
            floorButton3.setPrefWidth(50);
            floorButton3.setStyle("-fx-background-color:  #fff");
            floorButton4.setPrefWidth(44);
            floorButton4.setPrefWidth(50);
            floorButton4.setStyle("-fx-background-color:  #fff");
            floorButton5.setPrefWidth(44);
            floorButton5.setPrefWidth(50);
            floorButton5.setStyle("-fx-background-color:  #fff");
        }

        @FXML void onFloorClicked1(ActionEvent event) {
            neurtalizeButtons();
            floorButton1.setPrefWidth(64);
            floorButton1.setPrefWidth(71);
            floorButton1.setStyle("-fx-background-color: #f6bd38");
            mapImage.setImage(floor1);
            current_floor = 1;
            drawNodesEdges();
        }

        @FXML void onFloorClicked2(ActionEvent event) {
            neurtalizeButtons();
            floorButton2.setPrefWidth(64);
            floorButton2.setPrefWidth(71);
            floorButton2.setStyle("-fx-background-color: #f6bd38");
            mapImage.setImage(floor2);
            current_floor = 2;
            drawNodesEdges();
        }

        @FXML void onFloorClicked3(ActionEvent event) {
            neurtalizeButtons();
            floorButton3.setPrefWidth(64);
            floorButton3.setPrefWidth(71);
            floorButton3.setStyle("-fx-background-color: #f6bd38");
            mapImage.setImage(floor3);
            current_floor = 3;
            drawNodesEdges();
        }

        @FXML void onFloorClicked4(ActionEvent event) {
            neurtalizeButtons();
            floorButton4.setPrefWidth(64);
            floorButton4.setPrefWidth(71);
            floorButton4.setStyle("-fx-background-color: #f6bd38");
            mapImage.setImage(floor4);
            current_floor = 4;
            drawNodesEdges();
        }

        @FXML void onFloorClicked5(ActionEvent event) {
            neurtalizeButtons();
            floorButton5.setPrefWidth(64);
            floorButton5.setPrefWidth(71);
            floorButton5.setStyle("-fx-background-color: #f6bd38");
            mapImage.setImage(floor5);
            current_floor = 5;
            drawNodesEdges();
        }
        //#f6bd38 - yellow button color

        @FXML void onUpClicked(ActionEvent event) {
            neurtalizeButtons();
            if(current_floor != 5){
                current_floor += 1;
                if(current_floor == 1) {
                    neurtalizeButtons();
                    floorButton1.setPrefWidth(64);
                    floorButton1.setPrefWidth(71);
                    floorButton1.setStyle("-fx-background-color: #f6bd38");
                    mapImage.setImage(floor1);
                    current_floor = 1;
                    drawNodesEdges();
                }
                if(current_floor == 2) {
                    neurtalizeButtons();
                    floorButton2.setPrefWidth(64);
                    floorButton2.setPrefWidth(71);
                    floorButton2.setStyle("-fx-background-color: #f6bd38");
                    current_floor = 2;
                    drawNodesEdges();
                }
                if(current_floor == 3) {
                    neurtalizeButtons();
                    floorButton3.setPrefWidth(64);
                    floorButton3.setPrefWidth(71);
                    floorButton3.setStyle("-fx-background-color: #f6bd38");
                    mapImage.setImage(floor3);
                    current_floor = 3;
                    drawNodesEdges();
                }
                if(current_floor == 4) {
                    neurtalizeButtons();
                    floorButton4.setPrefWidth(64);
                    floorButton4.setPrefWidth(71);
                    floorButton4.setStyle("-fx-background-color: #f6bd38");
                    mapImage.setImage(floor4);
                    current_floor = 4;
                    drawNodesEdges();

                }
                if(current_floor == 5) {
                    neurtalizeButtons();
                    floorButton5.setPrefWidth(64);
                    floorButton5.setPrefWidth(71);
                    floorButton5.setStyle("-fx-background-color: #f6bd38");
                    mapImage.setImage(floor5);
                    current_floor = 5;
                    drawNodesEdges();
                }
            }
        }

        @FXML void onDownClicked(ActionEvent event) {
            if(current_floor != 1){
                current_floor -= 1;
                current_floor += 1;
                if(current_floor == 1) {
                    neurtalizeButtons();
                    floorButton1.setPrefWidth(64);
                    floorButton1.setPrefWidth(71);
                    floorButton1.setStyle("-fx-background-color: #f6bd38");
                    mapImage.setImage(floor1);
                    current_floor = 1;
                    drawNodesEdges();
                }
                if(current_floor == 2) {
                    neurtalizeButtons();
                    floorButton2.setPrefWidth(64);
                    floorButton2.setPrefWidth(71);
                    floorButton2.setStyle("-fx-background-color: #f6bd38");
                    current_floor = 2;
                    drawNodesEdges();
                }
                if(current_floor == 3) {
                    neurtalizeButtons();
                    floorButton3.setPrefWidth(64);
                    floorButton3.setPrefWidth(71);
                    floorButton3.setStyle("-fx-background-color: #f6bd38");
                    mapImage.setImage(floor3);
                    current_floor = 3;
                    drawNodesEdges();
                }
                if(current_floor == 4) {
                    neurtalizeButtons();
                    floorButton4.setPrefWidth(64);
                    floorButton4.setPrefWidth(71);
                    floorButton4.setStyle("-fx-background-color: #f6bd38");
                    mapImage.setImage(floor4);
                    current_floor = 4;
                    drawNodesEdges();
                }
                if(current_floor == 5) {
                    neurtalizeButtons();
                    floorButton5.setPrefWidth(64);
                    floorButton5.setPrefWidth(71);
                    floorButton5.setStyle("-fx-background-color: #f6bd38");
                    mapImage.setImage(floor5);
                    current_floor = 5;
                    drawNodesEdges();
                }
            }
        }

        @FXML void onHelpClicked(ActionEvent event) {
        }

        @FXML void onStaffClicked(ActionEvent event) {
        }

        @FXML void onZoomInClicked(ActionEvent event) {
            this.zoomer.zoomIn();
        }

        @FXML void onZoomOutClicked(ActionEvent event) {
            Node content = scrollPane.getContent();
            this.zoomer.zoomOut();
        }

        public JFXButton getFloor2() {return floorButton2;}

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            zoomer = new MapZoomer(mapImage, scrollPane);
        }

    public void drawNodesEdges() {

        String floor = "0" + current_floor;

        Group group = new Group();

        group.getChildren().clear();

        group.getChildren().add(mapImage);;

        MapEditingTasks tester = new MapEditingTasks(group);

        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodes();

        for(NodeData data : nd) {
            Circle circle1 = new Circle(data.getxCoordinate(), data.getyCoordinate(), 25);
            circle1.setStroke(Color.ORANGE);
            circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
            if(data.getNodeType().equals("ELEV")) {
                circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.5));}
            circle1.setVisible(false);
            if(data.getNodeID().substring(data.getNodeID().length()-2).equals(floor)) {circle1.setVisible(true);}
            group.getChildren().add(circle1);
        }

        Set<EdgeData> ed = dbc.getAllEdges();

        for(EdgeData data : ed) {
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
                    if(data.getEdgeID().substring(data.getEdgeID().length()-2).equals(floor)) {line1.setVisible(true);}
                    group.getChildren().add(line1);
                }
            }
        }

        addNodeRadio.setOnAction(e -> tester.drawNodes());
        removeNodeRadio.setOnAction(e -> tester.removeNodes(mapImage, current_floor));
        addEdgeRadio.setOnAction(e -> tester.addEdge(mapImage, current_floor));
        removeEdgeRadio.setOnAction(e -> tester.removeEdge(mapImage, current_floor));

        scrollPane.setContent(group);
    }
}
