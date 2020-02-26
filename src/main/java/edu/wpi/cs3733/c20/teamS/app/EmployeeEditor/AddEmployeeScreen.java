package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddEmployeeScreen {
    private EmployeeEditingScreenController controller;
    private Stage stage;
    private Scene scene;

    public AddEmployeeScreen(EmployeeEditingScreenController controller){
        this.stage = new Stage();
        this.controller = controller;
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AddEmployeeScreen.fxml"));
        loader.setControllerFactory(c -> {
            AddEmployeeScreenController cont = new AddEmployeeScreenController(this.controller);
            return cont;
        });

        try{
            Parent root = loader.load();
            this.scene = new Scene(root);
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    private void show(){
        stage.setScene(scene);
        stage.show();
    }

    public static void showDialog(EmployeeEditingScreenController controller){
        AddEmployeeScreen screen = new AddEmployeeScreen(controller);
        screen.show();
    }
}
