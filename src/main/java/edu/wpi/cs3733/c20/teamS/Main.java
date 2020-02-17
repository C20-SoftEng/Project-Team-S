package edu.wpi.cs3733.c20.teamS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

  public void start(Stage primaryStage) {
    try {
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/FXML/UI_client.fxml"));
      Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
      // scene.getStylesheets().add(getClass().getResource("").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    App.launch();
  }
}
