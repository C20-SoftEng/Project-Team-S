package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.pathDisplaying.MainScreenController;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Simple screen for main client ui
 */
public class MainToLoginScreen {
    private MainScreenController ui;
    private Scene scene;
    private Stage stage;

    public MainToLoginScreen(Stage stage, IPathfinder pathAlgorithm) {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UI_client.fxml"));
        loader.setControllerFactory(c -> {
            this.ui = new MainScreenController(stage, pathAlgorithm);
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
    public void show() {
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}