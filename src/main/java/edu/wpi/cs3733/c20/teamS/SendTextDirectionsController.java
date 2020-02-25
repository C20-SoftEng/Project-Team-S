package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

public class SendTextDirectionsController {

    @FXML
    private Label sendDirectionsLabel;

    @FXML
    private Label descLabel;

    @FXML
    private JFXPasswordField emailField;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private MenuButton carrierSelector;

    @FXML
    private JFXPasswordField phoneNumberField;

    @FXML
    private JFXButton sendEmailButton;

    @FXML
    private JFXButton sendTextButton;

    @FXML
    void OnSprintClicked(ActionEvent event) {
        carrierSelector.setText("Sprint");
    }

    @FXML
    void OnTMobileClicked(ActionEvent event) {
        carrierSelector.setText("T-Mobile");
    }

    @FXML
    void onATTClicked(ActionEvent event) {
        carrierSelector.setText("AT&T");
    }

    @FXML
    void onDoneClicked(ActionEvent event) {

    }

    @FXML
    void onSendEmailClicked(ActionEvent event) {

    }

    @FXML
    void onSendTextClicked(ActionEvent event) {

    }

    @FXML
    void onVerizonClicked(ActionEvent event) {
        carrierSelector.setText("Verizon");
    }

}
