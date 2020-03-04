package edu.wpi.cs3733.c20.teamS;

import animatefx.animation.FadeOut;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Simple screen for main client ui
 */
public class MainToLoginScreen extends BaseScreen {

    private Scene scene;
    private Stage stage;

    public MainToLoginScreen() {
        this.stage = Settings.get().getPrimaryStage();
        this.scene = stage.getScene();
        Settings.get().getMainScreenController().setDirectVisible();

        Settings set = Settings.get();
        if (this.scene == null){
            this.scene = new Scene(Settings.get().getMainScreenRoot());
        }
        else {
            this.scene.setRoot(Settings.get().getMainScreenRoot());
        }

        this.show();

    }
    private void show() {
        stage.setScene(scene);
        puggy.register(scene, Event.ANY);
        stage.show();
        Settings.get().getMainScreenController().clearPathDisplay();
    }
    public static void showDialog() {
        puggy.play();
        MainToLoginScreen screen = new MainToLoginScreen();
    }


}