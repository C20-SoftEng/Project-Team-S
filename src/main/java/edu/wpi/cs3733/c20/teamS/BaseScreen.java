package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.emergency.EmergencyScreen;
import edu.wpi.cs3733.c20.teamS.utilities.FireWatchDog;
import edu.wpi.cs3733.c20.teamS.utilities.UIWatchPug;
import javafx.util.Duration;

public abstract class BaseScreen {
    public static final UIWatchPug puggy;
    //public static final FireWatchDog fireWatch;

    static {
        //Stage stage = new Stage();
        puggy = new UIWatchPug(new Duration(10000),() -> {
            MainStartScreen.showDialog();
        });

//        fireWatch = new FireWatchDog(new Duration(1000),()->{
//           if(SerialPoller.runSensor()){
//               //System.out.println("Emergency Detected");
//               EmergencyScreen es = new EmergencyScreen();
//           }else{
//               //System.out.println("No emergency");
//           }
//
////           SerialPoller.serial.getInputStream().
//
//        });
    }

    //public void close(){}
}

