package edu.wpi.cs3733.c20.teamS.serviceRequests;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

/**
 * The Screen should be able to load active ServiceData from database
 * Parse them into corresponding ServiceRequest and display them inside the TableView of the screen
 * Also, it should update the status of selected ServiceRequest upon clicking "Completed"
 */
public class ActiveServiceRequestScreen {
    private ActiveServiceRequestScreenController screenController;
    //private DatabaseController databaseController;
    private ObservableList<ServiceRequest> activeRequests;
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

    public ActiveServiceRequestScreen(Stage stage, ObservableList<ServiceRequest> requests){
        this.stage = stage;
        this.activeRequests = requests;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ActiveServiceRequestScreen.fxml"));
        loader.setControllerFactory(c -> {
            this.screenController = new ActiveServiceRequestScreenController(this.stage, this.activeRequests);
            return this.screenController;
        });

        try{
            Parent root = loader.load();
            this.scene = new Scene(root);
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }

        this.show();
    }

    public void show(){
        this.stage.setScene(this.scene);
        this.screenController.showActiveRequests();
        this.stage.show();
    }
}
