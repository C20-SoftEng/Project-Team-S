package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreditsPageController {

    @FXML
    JFXButton goBackButton;


    @FXML
    void GoBack() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        // do what you have to do
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/AboutMe.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            //Parent  root1 = fxmlLoader.getRoot();
            Stage window = new Stage();
            window.initModality(Modality.WINDOW_MODAL);
            window.setFullScreen(false);
            window.setScene(new Scene(root));
            window.setResizable(false);


            window.show();
        } catch (IOException e) {
            System.out.println("Can't load new window");
        }

    }

    public CreditsPageController(){}

}
