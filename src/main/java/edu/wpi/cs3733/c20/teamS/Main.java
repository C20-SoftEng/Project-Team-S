package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;

import edu.wpi.cs3733.c20.teamS.pathfinding.A_Star;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  public void start(Stage primaryStage) {
    DatabaseController dbc = new DatabaseController();
    dbc.importStartUpData();

    MainToLoginScreen test = new MainToLoginScreen(primaryStage, new A_Star());
  }

  public static void main(String[] args) {
    App.launch();
  }
}
