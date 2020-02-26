package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCapabilityScreen {
    private Stage stage;
    private Scene scene;
    private EmployeeData employee;
    private CapabilityEditingScreenController controller;

    public AddCapabilityScreen(EmployeeData employee, CapabilityEditingScreenController controller){
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        this.employee = employee;
        this.controller = controller;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AddCapabilityScreen.fxml"));

        loader.setControllerFactory(c -> {
            AddCapabilityScreenController cont = new AddCapabilityScreenController(this.employee, this.controller);
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


    public static void showDialog(EmployeeData employee, CapabilityEditingScreenController controller){
        AddCapabilityScreen screen = new AddCapabilityScreen(employee,controller);
        screen.show();
    }
}
