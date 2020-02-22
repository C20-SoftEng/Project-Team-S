package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TwoFactorScreenController {



    @FXML
    private Label twoFactorLabel;

    @FXML
    private JFXPasswordField twoFactorCode;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton enter;

    @FXML
    private Label last4label;

    @FXML
    void onCancelClicked(ActionEvent event) {

    }

    @FXML
    void onEnterClicked(ActionEvent event) {
        System.out.println("Yert");
    }

}
