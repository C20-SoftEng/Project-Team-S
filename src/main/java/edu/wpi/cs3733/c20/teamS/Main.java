package edu.wpi.cs3733.c20.teamS;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {

  public void start(Stage primaryStage) {
    try {
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/FXML/UI_client.fxml"));
      Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
      // scene.getStylesheets().add(getClass().getResource("").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle("Hospital UI");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    App.launch();
  }

}
