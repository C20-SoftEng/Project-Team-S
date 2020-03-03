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

        fireWatch = new FireWatchDog(new Duration(100),()->{
           if(SerialTest.runSensor()){
               EmergencyScreen es = new EmergencyScreen();
           };
        });
    }

    public void close(){}
}

