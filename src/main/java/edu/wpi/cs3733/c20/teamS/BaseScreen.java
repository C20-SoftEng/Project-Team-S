package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.utilities.UIWatchPug;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class BaseScreen {
    public static final UIWatchPug puggy;
    private static Stage superStage;

    static {
        //Stage stage = new Stage();
        puggy = new UIWatchPug(new Duration(5000),() -> {
            MainStartScreen.showDialog(superStage);
        });
    }

    static void setSuperStage(Stage stage){
        superStage = stage;
    }

    public void close(){}
}

