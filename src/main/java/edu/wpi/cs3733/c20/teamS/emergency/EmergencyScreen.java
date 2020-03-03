package edu.wpi.cs3733.c20.teamS.emergency;

import edu.wpi.cs3733.c20.teamS.Settings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmergencyScreen {

    private Scene scene;

    public EmergencyScreen(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/EmergencyAlert.fxml"));
        loader.setControllerFactory(c -> {
            EmergencyController controller = new EmergencyController(Settings.primaryStage);
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
        Settings.primaryStage.setScene(scene);
        Settings.primaryStage.show();
    }
}
