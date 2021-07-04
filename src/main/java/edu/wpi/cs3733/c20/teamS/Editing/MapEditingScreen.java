package edu.wpi.cs3733.c20.teamS.Editing;


import edu.wpi.cs3733.c20.teamS.BaseScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MapEditingScreen extends BaseScreen{
    private Scene scene;
    private Stage stage;

    public MapEditingScreen() {
        DatabaseController dbc = new DatabaseController();
        dbc.autoCommit(false);
        this.stage = Settings.get().getPrimaryStage();
        this.scene = this.stage.getScene();

        if(this.scene == null){
            this.scene = new Scene(Settings.get().getEmployeeRoot());
        }
        else {
            this.scene.setRoot(Settings.get().getEmployeeRoot());
        }

        this.show();
    }

    public void show() {
        stage.setScene(scene);
        //stage.setMaximized(true);
        //stage.initStyle(StageStyle.UNDECORATED);
        //Settings.openWindows.add(this.stage);
        BaseScreen.puggy.register(scene, Event.ANY);
        Settings.get().getEditScreenController().fakeInitialize();
        stage.show();
    }

    public static void showDialog(){
        new MapEditingScreen();
    }
}