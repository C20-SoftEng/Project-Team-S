package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AddEmployeeScreenController implements Initializable {
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private ComboBox<Integer> accessLevel;
    @FXML private TextField firstName;
    @FXML private TextField lastName;

    @FXML private JFXButton addEmployeeButton;
    @FXML private JFXButton cancelButton;

    private EmployeeEditingScreenController controller;

    public void initialize(URL location, ResourceBundle resources){
        this.accessLevel.getItems().addAll(1,2,3);
    }

    public AddEmployeeScreenController(EmployeeEditingScreenController controller){
        this.controller = controller;
    }

    @FXML void onAddEmployeeClicked(){
//        EmployeeData employeeToAdd = new EmployeeData(3,this.username.getText(), this.password.getText(),
//                this.accessLevel.getValue(), this.firstName.getText(), this.lastName.getText());
//        this.controller.update(employeeToAdd);
        EmployeeData employeeToAdd = new EmployeeData(this.username.getText(), this.password.getText(),
                this.accessLevel.getValue(), this.firstName.getText(), this.lastName.getText());
        DatabaseController dbController = new DatabaseController();
        boolean employeeAlreadyExist = false;
        Set<EmployeeData> existingEmployees = dbController.getAllEmployeeData();
        for(EmployeeData em : existingEmployees){
            if(employeeToAdd.getUsername().equals(em.getUsername())){
                employeeAlreadyExist = true;
                break;
            }
        }

        if(!employeeAlreadyExist) {
            dbController.addEmployee(employeeToAdd);
            this.controller.update();
        }else{
//            System.out.println("Employee " + employeeToAdd.getUsername() + "already exist.");
//            System.out.println("Please try a different username");
            InvalidUsernameDialog.showDialog(employeeToAdd);
        }
    }

    @FXML void onCancelClicked(){
        Stage toClose = (Stage)cancelButton.getScene().getWindow();
        toClose.close();
    }
}
