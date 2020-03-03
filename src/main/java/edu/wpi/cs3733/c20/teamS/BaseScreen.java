package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.emergency.EmergencyScreen;
import edu.wpi.cs3733.c20.teamS.utilities.FireWatchDog;
import edu.wpi.cs3733.c20.teamS.utilities.UIWatchPug;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class BaseScreen {
    public static final UIWatchPug puggy;
    public static final FireWatchDog fireWatch;

    static {
        //Stage stage = new Stage();
        puggy = new UIWatchPug(new Duration(5000000),() -> {
            MainStartScreen.showDialog();
        });

        fireWatch = new FireWatchDog(new Duration(10000),()->{
           if(SerialTest.runSensor()){
               System.out.println("Emergency Detected");
               EmergencyScreen es = new EmergencyScreen();
           }else{
               System.out.println("No emergency");
           };
        });
    }

    public void close(){}
}

