package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import java.io.IOException;

public class MapEditingScreen {
    private EditScreenController ui;
    private Scene scene;
    private Stage stage;
    private Employee loggedIn;

    public MapEditingScreen(Stage stage, Employee employee) {
        this.loggedIn = employee;

        DatabaseController dbc = new DatabaseController();
        dbc.autoCommit(false);
        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UI_employee.fxml"));
        loader.setControllerFactory(c -> {
            this.ui = new EditScreenController(stage, loggedIn);
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
        stage.setFullScreen(true);

    }

    public void show() {
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}