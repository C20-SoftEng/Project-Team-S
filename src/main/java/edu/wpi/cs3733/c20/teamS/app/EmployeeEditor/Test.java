package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import edu.wpi.cs3733.c20.teamS.database.*;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.*;
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

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//    ObservableList<ServiceRequest> testSet = FXCollections.observableArrayList();
//    RideServiceRequest testServiceRequest = new RideServiceRequest(1);
//    DrugServiceRequest testServiceRequest2 = new DrugServiceRequest(2);
//    Employee tom = new Employee(1, "Tom");
//    Employee patrickStar = new Employee(2,"PatrickStar");
//    testServiceRequest.assignTo(tom);
//    testServiceRequest2.assignTo(patrickStar);
//    testServiceRequest.setMessage("Kobe is on helicopter");
//    testServiceRequest2.setMessage("Where is SpongeBob");
//    testServiceRequest.setLocation("Morgue");
//    testServiceRequest2.setLocation("BikiniBottom");
//    testSet.add(testServiceRequest);
//    testSet.add(testServiceRequest2);
//    ActiveServiceRequestScreen testScreen = new ActiveServiceRequestScreen(primaryStage, testSet);
//      ObservableList<EmployeeData> testSet = FXCollections.observableArrayList();
//      EmployeeData spongeBob = new EmployeeData(1, "SpongeBob", "Pineapple",
//              2, "Sponge", "Bob" );
//      testSet.add(spongeBob);
//      EmployeeEditingScreen.showDialog(testSet);
        DatabaseController dbController = new DatabaseController();
        dbController.importStartUpData();
        EmployeeEditingScreen.showDialog();
    }
}
