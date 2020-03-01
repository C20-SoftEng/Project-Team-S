package edu.wpi.cs3733.c20.teamS;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStartScreen extends BaseScreen {

    private  MainStartScreenController ui;
   private  Stage stage;
   private Scene scene;


    public MainStartScreen(Stage stage) {

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SplashScreen.fxml"));
        loader.setControllerFactory(c-> {
            this.ui = new MainStartScreenController();
            return this.ui;
        });
        try {
            Parent root = loader.load();
            this.scene = new Scene(root);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //ui.updateFloorDisplay();
        this.show();
        stage.setFullScreen(true);
    }
    private void show() {
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void showDialog(Stage mainScreen) {
        MainStartScreen screen = new MainStartScreen(mainScreen);
        for(Stage s : Settings.openWindows){
            s.close();
            Settings.openWindows.remove(s);
        }
        puggy.pause();
        //screen.show();
    }




    }


