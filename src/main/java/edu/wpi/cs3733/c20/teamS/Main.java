package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.Editing.MapEditingScreen;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  public void start(Stage primaryStage) {
    DatabaseController dbc = new DatabaseController();
    dbc.importStartUpData();
    dbc.addServiceRequestData(new ServiceData("YEET","Big yeets only", "Hello there", "Daterr",1,"Hi"));
    dbc.addServiceRequestData(new ServiceData("DOOT","Big Doots only", "Hello there, general kenobi", "I barely know her",2,"No"));
    dbc.addServiceRequestData(new ServiceData("MACE","COMPLETE", "Hello theddede", "I barely know her",2,"No"));
    //mainToLoginScreen test = new mainToLoginScreen(primaryStage);
    Employee bogus = new Employee(1, "Jay");
    MapEditingScreen test = new MapEditingScreen(primaryStage, bogus);
  }

  public static void main(String[] args) {
    App.launch();
  }
}
