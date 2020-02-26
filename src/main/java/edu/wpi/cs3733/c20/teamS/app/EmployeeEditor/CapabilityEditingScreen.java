package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CapabilityEditingScreen {
    private Stage stage;
    private Scene scene;
    private EmployeeData employee;

    public CapabilityEditingScreen(EmployeeData employee){
        this.stage = new Stage();
        this.employee = employee;
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/CapabilityEditingScreen.fxml"));

        loader.setControllerFactory(c -> {
            CapabilityEditingScreenController cont = new CapabilityEditingScreenController(this.employee);
            return cont;
        });

        try{
            Parent root = loader.load();
            this.scene = new Scene(root);
        }catch(IOException ex){
            throw new RuntimeException();
        }
    }

    private void show(){

        stage.setScene(scene);

        stage.show();
    }
    public static void showDialog(EmployeeData employee){
        CapabilityEditingScreen screen = new CapabilityEditingScreen(employee);
        screen.show();
    }
}
