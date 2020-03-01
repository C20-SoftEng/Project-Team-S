package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        RideRequestScreen.showDialog(primaryStage)
//                .subscribe(next -> System.out.println(next.result() + " result!"));
        DatabaseController dbController = new DatabaseController();
        dbController.importStartUpData();
        //Adding ServiceRequests
//        ServiceData testService1 = new ServiceData("MTNC", "ASSIGNED", "Good Morning",
//                null, 2, null);
        ServiceData testService1 = new ServiceData("MTNC", "CREATED", "Good Morning",
                null, 0, null);
        dbController.addServiceRequestData(testService1);
        ActiveServiceRequestScreen.showDialog();
    }
}
