package edu.wpi.cs3733.c20.teamS.emergency;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmergencyScreen {

    private Stage stage;
    private Scene scene;

    public EmergencyScreen(Stage stage){
        this.stage = stage;
        this.scene = stage.getScene();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/EmergencyAlert.fxml"));
        loader.setControllerFactory(c -> {
            EmergencyController controller = new EmergencyController(stage);
            return controller;
        });
        try {
            Parent root = loader.load();
            if(this.scene == null){
                this.scene = new Scene(root);
            }
            else{
                this.scene.setRoot(root);
            }

        }
        catch (
                IOException ex) {
            throw new RuntimeException(ex);
        }
        this.show();
    }

    public void show() {
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
