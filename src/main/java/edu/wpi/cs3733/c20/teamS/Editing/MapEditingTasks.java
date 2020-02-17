package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MapEditingTasks {
    private AtomicInteger result = new AtomicInteger();
    private int counter = 0;
    private NodeData startNode;
    private NodeData endNode;
    MapEditController ui;
    Group group;

    public MapEditingTasks(Group group) {
        this.group = group;
    }

    public void setNode(NodeData data) {
        if(counter % 2 == 0) {startNode = data;}
        if(counter % 2 != 0) {endNode = data;}
        counter++;
    }


    public void drawNodes() {
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showInfo();
                if(result.get() == 2) {
                    //popup node info (cancel -> delete circle, ok -> save node)  floor,building,nodetype,longname,shortname
                    Circle circle1 = new Circle(event.getX(), event.getY(), 25);
                    circle1.setStroke(Color.ORANGE);
                    circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
                    if(ui.getNodeType().getText().equals("ELEV")) {
                        circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.5));}
                    String num = "";
                    DatabaseController dbc = new DatabaseController();
                    Set<NodeData> nd = dbc.getAllNodes();
                    int max = 0;
                    for(NodeData data: nd) {
                        String order = "";

                        if((data.getFloor() == Integer.parseInt(ui.getFloor().getText())) && (data.getNodeType().equals(ui.getNodeType().getText().toUpperCase()))) {
                            order = data.getNodeID().substring(5,8);
                            if(Integer.parseInt(order) > max) {
                                max = Integer.parseInt(order);}
                        }
                    }
                    int number = max + 1;
                    if(number < 10) {num = "00" + number;}
                    if(number >= 10) {num = "0" + number;}
                    NodeData data = new NodeData("S" + ui.getNodeType().getText().toUpperCase() + num + "0" +
                            ui.getFloor().getText(),event.getX(),event.getY(),Integer.parseInt(ui.getFloor().getText()),
                            ui.getBuilding().getText(), ui.getNodeType().getText().toUpperCase(), ui.getLongName().getText(),ui.getShortName().getText());
                    group.getChildren().add(circle1);
                    dbc.addNode(data);
                }
            }
        });
    }

    Scene scene;
    public void showInfo() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/mapEdit.fxml"));
        loader.setControllerFactory(c -> {
            this.ui = new MapEditController();
            return this.ui;
        });

        try {
            Parent root = loader.load();
            this.scene = new Scene(root);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        result.set(0);
        ui.getCancel().setOnAction(e -> {result.set(1); stage.close(); });
        ui.getOk().setOnAction(e ->  { result.set(2); stage.close();});

        stage.setScene(scene);
        stage.showAndWait();

    }

    public void removeNodes(ImageView imageView, int current_floor) {
        String floornum = "0" + current_floor;

        group.getChildren().clear();

        group.getChildren().add(imageView);

        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodes();

        for (NodeData data : nd) {
            if (data.getNodeID().substring(data.getNodeID().length() - 2).equals(floornum)) {
                Circle circle1 = new Circle(data.getxCoordinate(), data.getyCoordinate(), 25);
                circle1.setStroke(Color.ORANGE);
                circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
                if(data.getNodeType().equals("ELEV")) {
                    circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.5));}
                circle1.setOnMouseClicked(e-> {group.getChildren().remove(circle1);
                dbc.removeNode(data.getNodeID()); });
                group.getChildren().add(circle1);
            }
        }

        Set<EdgeData> ed = dbc.getAllEdges();

        for (EdgeData data : ed) {
            if (data.getEdgeID().substring(data.getEdgeID().length() - 2).equals(floornum)) {
                String start = data.getStartNode();
                String end = data.getEndNode();
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
                    line1.setStrokeWidth(5);
                    group.getChildren().add(line1);
                }
            }
        }

        group.setOnMouseClicked(null);
    }

    public void addEdge(ImageView imageView, int current_floor) {
        String floornum = "0" + current_floor;

        group.setOnMouseClicked(null);
        group.getChildren().clear();

        group.getChildren().add(imageView);

        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodes();
        AtomicInteger edgeCount = new AtomicInteger();

        for(NodeData data : nd) {
            if(data.getNodeID().substring(data.getNodeID().length()-2).equals(floornum)) {
                Circle circle1 = new Circle(data.getxCoordinate(), data.getyCoordinate(), 25);
                circle1.setStroke(Color.ORANGE);
                circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
                if(data.getNodeType().equals("ELEV")) {
                    circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.5));}
                circle1.setOnMouseClicked(e -> {setNode(data); edgeCount.getAndIncrement(); drawingEdge(edgeCount.get());});
                group.getChildren().add(circle1);
            }
        }

        Set<EdgeData> ed = dbc.getAllEdges();

        for(EdgeData data : ed) {
            if(data.getEdgeID().substring(data.getEdgeID().length()-2).equals(floornum)) {
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
                    group.getChildren().add(line1);
                }
            }
        }
    }

    private void drawingEdge(int edgeCount) {
        if(edgeCount != 0 && edgeCount % 2 == 0) {
            Line line1 = new Line();
            int startX = (int)startNode.getxCoordinate();
            int startY = (int)startNode.getyCoordinate();
            int endX = (int)endNode.getxCoordinate();
            int endY = (int)endNode.getyCoordinate();
            line1.setStartX(startX);
            line1.setStartY(startY);
            line1.setEndX(endX);
            line1.setEndY(endY);
            line1.setStroke(Color.BLUE);
            line1.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
            line1.setStrokeWidth(5);
            group.getChildren().add(line1);

            DatabaseController dbc = new DatabaseController();
            dbc.addEdge(new EdgeData(startNode.getNodeID() + "_" + endNode.getNodeID(), startNode.getNodeID(), endNode.getNodeID()));
        }
    }

    public void removeEdge(ImageView imageView, int current_floor) {
        String floornum = "0" + current_floor;

        group.getChildren().clear();

        group.getChildren().add(imageView);

        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodes();

        for (NodeData data : nd) {
            if (data.getNodeID().substring(data.getNodeID().length() - 2).equals(floornum)) {
                Circle circle1 = new Circle(data.getxCoordinate(), data.getyCoordinate(), 25);
                circle1.setStroke(Color.ORANGE);
                circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
                if(data.getNodeType().equals("ELEV")) {
                    circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.5));}
                group.getChildren().add(circle1);
            }
        }

        Set<EdgeData> ed = dbc.getAllEdges();

        for (EdgeData data : ed) {
            if (data.getEdgeID().substring(data.getEdgeID().length() - 2).equals(floornum)) {
                String start = data.getStartNode();
                String end = data.getEndNode();
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
                    line1.setStrokeWidth(5);
                    line1.setOnMouseClicked(e-> {group.getChildren().remove(line1);
                    dbc.removeEdge(data.getEdgeID()); });
                    group.getChildren().add(line1);
                }
            }
        }

        group.setOnMouseClicked(null);
    }

    public void saveChanges() {
    }

    public void cancelChanges() {

    }
}
