package edu.wpi.cs3733.c20.teamS.serviceRequests;

import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.serviceRequests.screen.RideRequestDialog;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        RideRequestDialog.showDialog(primaryStage)
                .subscribe(result -> {
                    System.out.println("Event received.");
                });
        System.out.println("hello world");
    }
}
