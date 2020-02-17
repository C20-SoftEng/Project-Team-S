package edu.wpi.cs3733.c20.teamS.Editing;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

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
        MapEditingTasks tester = new MapEditingTasks(group);
        ui.getAddNode().setOnAction(e -> tester.drawNodes());

        ui.getScrollPane().setContent(group);

        this.show();
    }
    public void show() {
        stage.setScene(scene);
        stage.show();
    }
}
