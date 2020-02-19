package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.serviceRequests.ActiveServiceRequestScreen;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.ServiceRequest;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class App extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    ObservableList<ServiceRequest> testSet = FXCollections.observableArrayList();
    RideServiceRequest testServiceRequest = new RideServiceRequest(1);
    Employee tom = new Employee(1, "Tom");
    testServiceRequest.assignTo(tom);
    testServiceRequest.setMessage("Kobe is on helicopter");
    testServiceRequest.setLocation("Morgue");
    testSet.add(testServiceRequest);
    ActiveServiceRequestScreen testScreen = new ActiveServiceRequestScreen(primaryStage, testSet);
  }
}
