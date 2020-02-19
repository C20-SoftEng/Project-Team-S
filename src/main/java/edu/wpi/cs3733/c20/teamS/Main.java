package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.Editing.MapEditingScreen;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  public void start(Stage primaryStage) {
    DatabaseController dbc = new DatabaseController();
    dbc.importStartUpData();

    mainToLoginScreen test = new mainToLoginScreen(primaryStage);
    Employee bogus = new Employee(1, "Jay");
    //MapEditingScreen test = new MapEditingScreen(primaryStage, bogus);
  }

  public static void main(String[] args) {
    App.launch();
  }
}
