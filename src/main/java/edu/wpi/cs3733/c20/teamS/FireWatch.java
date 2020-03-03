package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.emergency.EmergencyScreen;
import edu.wpi.cs3733.c20.teamS.utilities.FireThread;
import javafx.application.Application;
import javafx.stage.Stage;

import static edu.wpi.cs3733.c20.teamS.SerialTest.sensor;

public class FireWatch extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        FireThread ft = new FireThread(1);
//        ft.run();
        EmergencyScreen es = new EmergencyScreen();

    }


}
