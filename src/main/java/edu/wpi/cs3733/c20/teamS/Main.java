package edu.wpi.cs3733.c20.teamS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        App.launch(Main.class, args);
    }

    @Override

    public void start(Stage primaryStage) {
        mainToLoginScreen test = new mainToLoginScreen(primaryStage);
    }
}