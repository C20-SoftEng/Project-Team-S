package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.utilities.UIWatchPug;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class PopUpScreen {
    public static final UIWatchPug puggy;
    public static Stage stage;

    static {
        //Stage stage = new Stage();
        puggy = new UIWatchPug(new Duration(10000),() -> close());
    }

    public static void close(){
        stage.close();
    }
}

