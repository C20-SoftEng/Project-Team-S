package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AssignEmployeeScreen {
    //private AssignEmployeeScreenController controller;
    private Stage stage;
    private Scene scene;
    private ServiceData tbAssigned;
    private ActiveServiceRequestScreenController controller;

    public AssignEmployeeScreen(ServiceData selected, ActiveServiceRequestScreenController controller){
        this.stage = new Stage();
        this.tbAssigned = selected;
        this.controller = controller;
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AssignEmployeeScreen.fxml"));
        loader.setControllerFactory(c -> {
            AssignEmployeeScreenController cont = new AssignEmployeeScreenController(this.tbAssigned, this. controller);
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

    public static void showDialog(ServiceData selected, ActiveServiceRequestScreenController controller){
        AssignEmployeeScreen screen = new AssignEmployeeScreen(selected, controller);
        screen.show();
    }
}
