package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.InterpreterServiceRequest;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class AboutMeController {

    @FXML
    private AnchorPane AboutMePane;

    @FXML
    private JFXButton goBack;



    @FXML
    void BackClicked() {
        Stage stage = (Stage) goBack.getScene().getWindow();
        // do what you have to do
        stage.close();
    }





    public AboutMeController(){}





}

