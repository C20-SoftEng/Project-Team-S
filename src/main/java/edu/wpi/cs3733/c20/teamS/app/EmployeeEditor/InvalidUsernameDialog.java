package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InvalidUsernameDialog {
    private EmployeeData employee;
    private Stage stage;
    private Scene scene;

    public InvalidUsernameDialog(EmployeeData employee){
        this.employee = employee;
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/InvalidUsernameDialog.fxml"));
        loader.setControllerFactory(c -> {
            InvalidUsernameDialogController cont = new InvalidUsernameDialogController(this.employee);
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
        InvalidUsernameDialog dialog = new InvalidUsernameDialog(employee);
        dialog.show();
    }

}
