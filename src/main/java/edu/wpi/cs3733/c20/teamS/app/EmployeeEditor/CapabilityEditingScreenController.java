package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class CapabilityEditingScreenController implements Initializable {
    @FXML private JFXTextField employeeID;
    @FXML private JFXTextField userName;
    @FXML private TableView<String> capabilitiesTable;
    @FXML private TableColumn<String, String> capabilitiesCol;
    @FXML private JFXButton addButton;
    @FXML private JFXButton removeButton;
    @FXML private JFXButton cancelButton;

    private EmployeeData employee;
    private ObservableList<String> capabilities;

    public void initialize(URL location, ResourceBundle resources){
        this.capabilities = FXCollections.observableArrayList();
        this.employeeID.setText(Integer.toString(this.employee.getEmployeeID()));
        this.userName.setText(this.employee.getUsername());
        this.update();

        //capabilitiesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        capabilitiesCol.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue()));

        capabilitiesTable.setItems(this.capabilities);
    }

    public CapabilityEditingScreenController(EmployeeData employee){
        this.employee = employee;
    }

    @FXML void onCancelClicked(){
        Stage toClose = (Stage)cancelButton.getScene().getWindow();
        toClose.close();
    }

    @FXML void onAddClicked(){

    }

    @FXML void onRemoveClicked(){

    }

    public void update(){
        DatabaseController dbController = new DatabaseController();
        this.capabilities.removeAll(this.capabilities);
        this.capabilities.addAll(dbController.getEmployeeCapabilities(this.employee.getEmployeeID()));
    }
}
