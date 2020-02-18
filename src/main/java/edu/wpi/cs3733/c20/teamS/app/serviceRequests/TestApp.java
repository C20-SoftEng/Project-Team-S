package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        RideRequestScreen.showDialog(primaryStage)
                .subscribe(next -> System.out.println(next.result() + " result!"));
    }
}
