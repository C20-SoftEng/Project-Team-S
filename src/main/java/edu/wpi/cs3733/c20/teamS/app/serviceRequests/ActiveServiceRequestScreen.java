package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Screen should be able to load active ServiceData from database
 * Parse them into corresponding ServiceRequest and display them inside the TableView of the screen
 * Also, it should update the status of selected ServiceRequest upon clicking "Completed"
 */
public class ActiveServiceRequestScreen {
    private ActiveServiceRequestScreenController screenController;
    //private DatabaseController databaseController;
    private ObservableList<ServiceData> activeRequests;
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

    public ActiveServiceRequestScreen(ObservableList<ServiceData> requests){

        //if(employee == null) ThrowHelper.illegalNull("employee");
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        //this.loggedIn = employee;
        this.activeRequests = requests;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ActiveServiceRequestScreen.fxml"));
        loader.setControllerFactory(c -> {
//            this.screenController = new ActiveServiceRequestScreenController(this.stage, this.activeRequests);
//            return this.screenController;
            ActiveServiceRequestScreenController cont = new ActiveServiceRequestScreenController(activeRequests);
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

    public static void showDialog(ObservableList<ServiceData> requests) {
        ActiveServiceRequestScreen screen = new ActiveServiceRequestScreen(requests);
        //screen.screenController.showActiveRequests();
        screen.show();
    }

}
