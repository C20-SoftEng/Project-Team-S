package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MapEditingScreen {
    private EditScreenController ui;
    private Scene scene;
    private Stage stage;
    public MapEditingScreen(Stage stage) {
        DatabaseController dbc = new DatabaseController();
        dbc.autoCommit(false);

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UI_employee.fxml"));
        loader.setControllerFactory(c -> {
            this.ui = new EditScreenController(stage);
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
        ui.getFloorButton2().fire();

        this.show();
    }
    public void show() {
        stage.setScene(scene);
        stage.show();
    }
}