package edu.wpi.cs3733.c20.teamS;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
/**
 * Simple screen for main client ui
 */
public class mainToLoginScreen {
    private mainScreenController ui;
    private Scene scene;
    private Stage stage;
    public mainToLoginScreen(Stage stage) {
        this.stage = stage;
        stage.setFullScreen(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UI_client.fxml"));
        loader.setControllerFactory(c -> {
            this.ui = new mainScreenController();
            return this.ui;
        });
        try {
            Parent root = loader.load();

            this.scene = new Scene(root);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        ui.drawNodesEdges();
        ui.getFloor2().fire();



        this.show();
    }
    public void show() {
        stage.setScene(scene);
        stage.show();
    }
}