package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Simple screen for main client ui
 */
public class MainToLoginScreen extends MainScreen {
    private MainScreenController ui;
    private Scene scene;
    private Stage stage;

    public MainToLoginScreen(Stage stage) {
        this.stage = stage;
        this.scene = stage.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UI_client.fxml"));
        loader.setControllerFactory(c -> {
            this.ui = new MainScreenController(stage);
            return this.ui;
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
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //ui.updateFloorDisplay();
        this.show();
        stage.setFullScreen(true);
        puggy.register(scene, Event.ANY);
    }
    public void show() {
        stage.setScene(scene);
        stage.setMaximized(true);
        puggy.play();
        stage.show();
    }
}