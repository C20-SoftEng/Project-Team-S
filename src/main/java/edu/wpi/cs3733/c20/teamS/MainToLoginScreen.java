package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

/**
 * Simple screen for main client ui
 */
public class MainToLoginScreen extends BaseScreen {
    private MainScreenController ui;
    private Scene scene;
    private Stage stage;

    public MainToLoginScreen(Stage stage) {
        this.stage = stage;
        this.scene = stage.getScene();
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UI_client.fxml"));
        Settings single = Settings.get();
        FXMLLoader loader = single.singleLoader;
//        loader.setControllerFactory(c -> {
//            this.ui = new MainScreenController(stage);
//            return this.ui;
//        });
        try {

            Parent root = single.root;
            //Parent root = loader.load();
            //this.scene = new Scene(root);

            if(this.scene == null){
                this.scene = new Scene(root);
                throw new IOException();
            }
            else {
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
        setSuperStage(stage);
    }
    private void show() {
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public static void showDialog(Stage mainScreen) {
        puggy.play();
        MainToLoginScreen screen = new MainToLoginScreen(mainScreen);
        //puggy.pause();
        //screen.show();
    }


}