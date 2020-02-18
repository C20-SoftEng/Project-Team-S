package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
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
        if ((id.getText().equals("admin")) && (pw.getText().equals("admin"))){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/UI_employee.fxml"));
                Parent root2 = (Parent) fxmlLoader.load();
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Welcome, " + id.getText());
                window.setScene(new Scene(root2));
                window.show();
            }catch (Exception e){
                System.out.println("Can't load new window");
            }
        }
        if (!(id.getText().equals("admin"))){
            wrongID.setVisible(true);
        }
        if (!pw.getText().equals("admin")){
            wrongPW.setVisible(true);
        }
    }

}
