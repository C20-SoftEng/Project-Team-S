package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStartScreen {

    private  MainStartScreenController ui;
   private  Stage stage;
   private Scene scene;


    public MainStartScreen(Stage stage) {

        this.stage = stage;
        this.scene = this.stage.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SplashScreen.fxml"));
        loader.setControllerFactory(c-> {
            this.ui = new MainStartScreenController();
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
    }
    public void show() {
        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.show();
    }




    }


