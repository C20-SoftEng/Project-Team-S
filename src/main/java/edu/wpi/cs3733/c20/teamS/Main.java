package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.pathfinding.AStar;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static final boolean START_ON_ADMIN_SCREEN = true;

    public void start(Stage primaryStage) {
        DatabaseController dbc = new DatabaseController();
        dbc.importStartUpData();

<<<<<<< HEAD
    new MainToLoginScreen(primaryStage, new AStar());
//    MapEditingScreen test = new MapEditingScreen(primaryStage,
//            new Employee(17, "Bob", AccessLevel.ADMIN));

  }

  public static void main(String[] args) {
    App.launch();
  }
=======
        if (START_ON_ADMIN_SCREEN)
            new MapEditingScreen(primaryStage, new Employee(17, "Bob", AccessLevel.ADMIN));
        else
            new MainToLoginScreen(primaryStage, new AStar());
    }
    //9003,staff,staff,2,Wilson,Wong
    public static void main(String[] args) {
        App.launch();
    }
>>>>>>> feature_leftClickAdd_rightClickDelete
}
