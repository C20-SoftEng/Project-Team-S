package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class InvalidUsernameDialogController implements Initializable {
    @FXML private JFXTextField invalidUsername;

    private EmployeeData employee;

    public void initialize(URL location, ResourceBundle resources){
        this.invalidUsername.setText(this.employee.getUsername());
    }

    public InvalidUsernameDialogController(EmployeeData employee){
        this.employee = employee;
    }
}
