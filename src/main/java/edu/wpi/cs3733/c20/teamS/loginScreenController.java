package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class loginScreenController {

    @FXML private JFXTextField id;

    @FXML private JFXTextField pw;

    @FXML private JFXButton cancel;

    @FXML private JFXButton enter;

    @FXML private Label wrongID;

    @FXML private Label wrongPW;

    @FXML void onCancelClicked(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * change this for later, just using admin
     */
    @FXML void onEnterClicked(ActionEvent event) {
        if (id. "admin" && pw.getText() == "admin"){
            //modality
        }
        else {
            wrongID.setVisible(true);
            wrongPW.setVisible(true);
        }
    }

}
