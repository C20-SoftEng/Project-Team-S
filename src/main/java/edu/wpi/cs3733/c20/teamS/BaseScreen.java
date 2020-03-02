package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.utilities.UIWatchPug;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class BaseScreen {
    public static final UIWatchPug puggy;

    static {
        //Stage stage = new Stage();
        puggy = new UIWatchPug(new Duration(30000),() -> {
            MainStartScreen.showDialog();
        });
    }

    public void close(){}
}

