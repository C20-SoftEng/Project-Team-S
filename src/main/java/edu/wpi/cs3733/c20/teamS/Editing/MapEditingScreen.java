package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import java.io.IOException;

public class MapEditingScreen {
    private Scene scene;
    private Stage stage;
    private Employee loggedIn;

    public MapEditingScreen() {
        this.loggedIn = Settings.loggedIn;

        DatabaseController dbc = new DatabaseController();
        dbc.autoCommit(false);

        this.stage = Settings.primaryStage;
        this.scene = this.stage.getScene();

        if(this.scene == null){
            this.scene = new Scene(Settings.employeeRoot);
        }
        else {
            this.scene.setRoot(Settings.employeeRoot);
        }

        this.show();
    }

    public void show() {
        stage.setScene(scene);
        stage.show();
    }

    public static void showDialog(){
        new MapEditingScreen();
    }
}