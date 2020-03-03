package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Simple screen for main client ui
 */
public class MainToLoginScreen extends BaseScreen {
    private MainScreenController ui;
    private Scene scene;
    private Stage stage;

    public MainToLoginScreen() {
        this.stage = Settings.primaryStage;
        this.scene = stage.getScene();

        Settings set = Settings.get();

        if (this.scene == null){
            this.scene = new Scene(Settings.mainScreenRoot);
        }
        else {
            this.scene.setRoot(Settings.mainScreenRoot);
        }

        this.show();

    }
    private void show() {
        stage.setScene(scene);
        puggy.register(scene, Event.ANY);
        stage.show();
        Settings.mainScreenController.clearPathDisplay();
    }
    public static void showDialog() {
        puggy.play();
        MainToLoginScreen screen = new MainToLoginScreen();
    }


}