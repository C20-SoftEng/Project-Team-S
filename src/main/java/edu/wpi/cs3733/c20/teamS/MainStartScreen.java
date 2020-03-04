package edu.wpi.cs3733.c20.teamS;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStartScreen extends BaseScreen {
    private  MainStartScreenController ui;
   private  Stage stage;
   private Scene scene;


    public MainStartScreen() {
        this.stage = Settings.get().getPrimaryStage();
        this.scene = this.stage.getScene();


        if (this.scene == null){
            this.scene = new Scene(Settings.get().getSplashRoot());
        }
        else {
            this.scene.setRoot(Settings.get().getSplashRoot());
        }

        this.show();
    }

    private void show() {
        stage.setScene(scene);
        stage.show();
    }

    public static void showDialog() {
        MainStartScreen screen = new MainStartScreen();
        for(Stage s : Settings.openWindows){
            s.close();
            //Settings.openWindows.remove(s);
        }
        Settings.openWindows.clear();
        puggy.pause();
        //screen.show();
    }

    }


