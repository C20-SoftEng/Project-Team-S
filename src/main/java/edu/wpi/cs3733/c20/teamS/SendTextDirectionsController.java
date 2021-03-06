package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.utilities.SendEmailDirectionsThread;
import edu.wpi.cs3733.c20.teamS.utilities.SendTextDirectionsThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

import java.util.List;
import java.util.Set;

public class SendTextDirectionsController {

    List<String> directions;

    public SendTextDirectionsController(List<String> directions){
        this.directions = directions;
    }

    @FXML
    private Label sendDirectionsLabel;

    @FXML
    private Label descLabel;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private MenuButton carrierSelector;

    @FXML
    private JFXTextField phoneNumberField;

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
        Stage stage = (Stage) descLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onSendEmailClicked(ActionEvent event) {
        if(emailField.getText().equals("")){
            return;
        }else{
            String email = emailField.getText();
            SendEmailDirectionsThread SEDT = new SendEmailDirectionsThread(this.directions,email);
            SEDT.start();

        }

    }

    @FXML
    void onSendTextClicked(ActionEvent event) {
        if(carrierSelector.getText().equals("Select Carrier for Number")){
            return;
        }else{
            String carrier = carrierSelector.getText();
            String number = phoneNumberField.getText();
            SendTextDirectionsThread STDT = new SendTextDirectionsThread(this.directions,number,carrier);
            STDT.start();
        }
    }

    @FXML
    void onVerizonClicked(ActionEvent event) {
        carrierSelector.setText("Verizon");
    }

}
