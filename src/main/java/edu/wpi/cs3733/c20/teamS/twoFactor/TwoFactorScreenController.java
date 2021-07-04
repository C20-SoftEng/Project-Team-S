package edu.wpi.cs3733.c20.teamS.twoFactor;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.AccessLevel;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

public class TwoFactorScreenController {

    Stage stage;
    Employee loggedIn;
    TwoFactorScreen tfaScreen;


    public TwoFactorScreenController(Stage stage, Employee loggedIn) {
        this.stage = stage;
        this.loggedIn = loggedIn;
    }

    @FXML
    private Label twoFactorLabel;

    @FXML
    private JFXPasswordField tfaCodeField;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton enterButton;

    @FXML
    private Label descLabel;

    @FXML
    private MenuButton carrierSelector;

    @FXML
    private JFXButton sendButton;

    public EmployeeData ed;

    @FXML
    void onCancelClicked(ActionEvent event) {
        this.stage.close();
    }

    @FXML
    void onVerizonClicked(ActionEvent event) {
        carrierSelector.setText("Verizon");
    }

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
    void onEnterClicked(ActionEvent event) {
        if(Integer.parseInt(tfaCodeField.getText()) == tfaScreen.tfaCode){
            tfaScreen.passedTFA();
        }else{
            descLabel.setText("Incorrect code");
            tfaCodeField.setStyle("-fx-background-color:RED");
        }
    }

    @FXML
    void onSendClicked(ActionEvent event) {
        System.out.println("Carrier selected is: " + carrierSelector.getText());
        if(carrierSelector.getText().equals("Carrier")){
            System.out.println("No carrier selected");
            return;
        }


        sendButton.setDisable(true);
        enterButton.setDisable(false);
        //tfaCodeField.setDisable(true);
        String carrier = carrierSelector.getText();
        tfaScreen.setCarrier(carrier);
        tfaScreen.sendTFA();
        carrierSelector.setDisable(true);
        descLabel.setText("Text sent to number on file");

    }

    public PublishSubject<DialogEvent<Employee>> dialogCompleted_;
    public Observable<DialogEvent<Employee>> dialogCompleted() {
        return dialogCompleted_;
    }
}

