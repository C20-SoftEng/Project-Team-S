package edu.wpi.teamname;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Label label = new Label("Text");
    label.setId("textLabel");
    label.setVisible(false);

    Button button = new Button("My Button");
    button.setId("showTextButton");
    button.setOnAction((e) -> label.setVisible(true));

    BorderPane root = new BorderPane();
    root.setRight(label);
    root.setLeft(button);

    Scene scene = new Scene(root, 200, 100);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
