package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.InterpreterServiceRequest;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;

public class AboutMeController {

    @FXML
    private AnchorPane AboutMePane;

    @FXML
    private JFXButton goBack;

    @FXML
    private JFXButton goCredits;



    @FXML
    void BackClicked() {
        Stage stage = (Stage) goBack.getScene().getWindow();
        // do what you have to do
        stage.close();
    }



    //Open up Credits Page
    @FXML
    void goToCredits(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/CreditsPage.FXML"));
            Parent root = (Parent) fxmlLoader.load();
            //Parent  root1 = fxmlLoader.getRoot();
            Stage window = new Stage();
            window.initModality(Modality.WINDOW_MODAL);
            window.setFullScreen(false);
            window.setScene(new Scene(root));
            window.setResizable(false);
            window.show();

            Stage stage = (Stage) goBack.getScene().getWindow();
            // do what you have to do
            stage.close();

        } catch (IOException e) {
            System.out.println("Can't load new window");
        }

    }





    public AboutMeController(){}





}

