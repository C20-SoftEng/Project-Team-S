package edu.wpi.cs3733.c20.teamS;

import com.sun.javafx.application.PlatformImpl;
import com.sun.javafx.css.StyleManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStartScreen extends BaseScreen {
    private  MainStartScreenController ui;
   private  Stage stage;
   private Scene scene;


    public MainStartScreen() {
        this.stage = Settings.get().getPrimaryStage();
        this.scene = this.stage.getScene();


        if(this.scene == null){
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


