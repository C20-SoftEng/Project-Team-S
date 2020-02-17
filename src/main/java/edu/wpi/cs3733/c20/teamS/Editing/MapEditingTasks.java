package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MapEditingTasks {
    private AtomicInteger result = new AtomicInteger();
    MapEditController ui;
    Group group;

    public MapEditingTasks(Group group) {
        this.group = group;
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
}
