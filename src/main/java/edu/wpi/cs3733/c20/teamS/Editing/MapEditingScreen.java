package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.PathDisplay;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class MapEditingScreen {
    private EditScreenController ui;
    private Scene scene;
    private Stage stage;
    public MapEditingScreen(Stage stage) {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MapEditingScreen.fxml"));
        loader.setControllerFactory(c -> {
            this.ui = new EditScreenController();
            return this.ui;
        });
        try {
            Parent root = loader.load();

            this.scene = new Scene(root);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Group group = new Group();
        ImageView mapImage = new ImageView();
        mapImage.setImage(new Image("images/Floors/HospitalFloor2.png"));
        group.getChildren().add(mapImage);

        String floor = "02";

        group.getChildren().clear();

        group.getChildren().add(mapImage);;

        PathDisplay tester = new PathDisplay(group);

        DatabaseController dbc = new DatabaseController();
        Set<NodeData> nd = dbc.getAllNodes();

        for(NodeData data : nd) {
            Circle circle1 = new Circle(data.getxCoordinate(), data.getyCoordinate(), 25);
            circle1.setStroke(Color.ORANGE);
            circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
            if(data.getNodeType().equals("ELEV")) {
                circle1.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.5));}
            circle1.setOnMouseClicked(e -> tester.setNode(data));
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

        MapEditingTasks test = new MapEditingTasks(group);
        ui.getAddNode().setOnAction(e -> test.drawNodes());

        ui.getScrollPane().setContent(group);

        this.show();
    }
    public void show() {
        stage.setScene(scene);
        stage.show();
    }
}
