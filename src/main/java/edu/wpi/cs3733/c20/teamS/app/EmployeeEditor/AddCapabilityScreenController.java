package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddCapabilityScreenController implements Initializable {
    @FXML private JFXTextField employeeID;
    @FXML private JFXTextField userName;
    @FXML private TableView<String> capsToAddTable;
    @FXML private TableColumn<String, String> capsToAddCol;
    @FXML private JFXButton cancelButton;
    @FXML private JFXButton addButton;

    private EmployeeData employee;
    private CapabilityEditingScreenController controller;
    private ObservableList<String> capsToAdd;


    public void initialize(URL location, ResourceBundle resources){
        this.employeeID.setText(Integer.toString(this.employee.getEmployeeID()));
        this.userName.setText(this.employee.getUsername());
        this.update();

        capsToAddCol.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue()));

        capsToAddTable.setItems(this.capsToAdd);
    }

    public AddCapabilityScreenController(EmployeeData employee, CapabilityEditingScreenController controller){
        this.employee = employee;
        this.controller = controller;
    }

    @FXML void onCancelClicked(){
        Stage toClose = (Stage) cancelButton.getScene().getWindow();
        toClose.close();
    }

    @FXML void onAddClicked(){
        String selected = this.capsToAddTable.getSelectionModel().getSelectedItem();
        DatabaseController dbController = new DatabaseController();
        dbController.addCapability(this.employee.getEmployeeID(), selected);
        this.controller.update();
        this.capsToAdd.remove(selected);
        //this.update();
    }

    public void update(){
        String[] capabilities = {"DRUG", "FLOR", "GIFT", "INTR", "JANI", "LSRT", "LNDR", "MTNC", "RIDE",
    "SECU", "TECH"};
        //this.capsToAdd.removeAll(this.capsToAdd);
        this.capsToAdd = FXCollections.observableArrayList(capabilities);
        DatabaseController dbController = new DatabaseController();
        this.capsToAdd.removeAll(dbController.getEmployeeCapabilities(this.employee.getEmployeeID()));
    }
}
