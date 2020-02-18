package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.Editing.MapEditingScreen;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  public void start(Stage primaryStage) {
    DatabaseController dbc = new DatabaseController();
    dbc.importStartUpData();

    mainToLoginScreen test = new mainToLoginScreen(primaryStage);
    //MapEditingScreen test = new MapEditingScreen(primaryStage);
  }

  public static void main(String[] args) {
    App.launch();
  }
}
