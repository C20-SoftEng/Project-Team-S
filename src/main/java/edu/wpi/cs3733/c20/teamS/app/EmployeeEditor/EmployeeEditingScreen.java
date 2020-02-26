package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import edu.wpi.cs3733.c20.teamS.app.serviceRequests.ActiveServiceRequestScreenController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Screen should be able to load EmployeeData from database
 * Parse and display them inside the TableView of the screen
 * Also, it should enable editing and update the attributes of selected Employee upon clicking "Save"
 */

public class EmployeeEditingScreen {
    //private EmployeeEditingScreenController screenController;
    //private DatabaseController databaseController;
    //private ObservableList<EmployeeData> employees;
    private Stage stage;
    private Scene scene;

    /*
    public ActiveServiceRequestScreen(DatabaseController dbController){
        this.databaseController = dbController;
    }
     */

    /*
    public Set<ServiceRequest> activeRequests(){
        return this.activeRequests;
    }
    */

//    public EmployeeEditingScreen(ObservableList<EmployeeData> employees){
//
//        //if(employee == null) ThrowHelper.illegalNull("employee");
//        this.stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setResizable(false);
//        //this.loggedIn = employee;
//        this.employees = employees;
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/EmployeeEditingScreen.fxml"));
//        loader.setControllerFactory(c -> {
////            this.screenController = new ActiveServiceRequestScreenController(this.stage, this.activeRequests);
////            return this.screenController;
//            EmployeeEditingScreenController cont = new EmployeeEditingScreenController(this.employees);
//            return cont;
//        });
//
//        try{
//            Parent root = loader.load();
//            this.scene = new Scene(root);
//        }catch(IOException ex){
//            throw new RuntimeException(ex);
//        }
//    }
//
//    private void show() {
//
//        stage.setScene(scene);
//
//        stage.show();
//    }

    public EmployeeEditingScreen(){

        //if(employee == null) ThrowHelper.illegalNull("employee");
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.setResizable(false);
        //this.loggedIn = employee;
        //this.employees = employees;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/EmployeeEditingScreen.fxml"));
        loader.setControllerFactory(c -> {
//            this.screenController = new ActiveServiceRequestScreenController(this.stage, this.activeRequests);
//            return this.screenController;
//            EmployeeEditingScreenController cont = new EmployeeEditingScreenController(this.employees);
            EmployeeEditingScreenController cont = new EmployeeEditingScreenController();
            return cont;
        });

        try{
            Parent root = loader.load();
            this.scene = new Scene(root);
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    private void show() {

        stage.setScene(scene);

        stage.show();
    }

//    public static void showDialog(ObservableList<EmployeeData> employees) {
//        EmployeeEditingScreen screen = new EmployeeEditingScreen(employees);
//        //screen.screenController.showActiveRequests();
//        screen.show();
//    }

    public static void showDialog() {
        EmployeeEditingScreen screen = new EmployeeEditingScreen();
        //screen.screenController.showActiveRequests();
        screen.show();
    }

}
