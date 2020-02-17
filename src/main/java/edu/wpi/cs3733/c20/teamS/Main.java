package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

  public void start(Stage primaryStage) {
    DatabaseController dbc = new DatabaseController();
    dbc.importStartUpData();

    mainToLoginScreen test = new mainToLoginScreen(primaryStage);
  }

  public static void main(String[] args) {
    App.launch();
  }
}
