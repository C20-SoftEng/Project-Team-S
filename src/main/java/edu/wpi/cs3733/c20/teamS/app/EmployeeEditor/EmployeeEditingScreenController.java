package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class EmployeeEditingScreenController {
    @FXML private TableView<EmployeeData> employeeTable;
    @FXML private JFXButton cancelButton;
    @FXML private TableColumn<EmployeeData, Integer> employeeIDCol;
    @FXML private TableColumn<EmployeeData, String> usernameCol;
    @FXML private TableColumn<EmployeeData, String> passwordCol;
    @FXML private TableColumn<EmployeeData, Integer> accessLevelCol;
    @FXML private TableColumn<EmployeeData, String> firstNameCol;
    @FXML private TableColumn<EmployeeData, String> lastNameCol;

    private ObservableList<EmployeeData> employees;

    @FXML
    public void initialize() {
        showEmployees();
    }

    public EmployeeEditingScreenController(ObservableList<EmployeeData> employees){
        this.employeeTable = new TableView<>();
        this.employees = employees;
    }

    /**
     * show Employees in the TableView
     */
    public void showEmployees(){
        employeeTable.setItems(this.employees);
        employeeIDCol = new TableColumn<>("Employee ID");
        usernameCol = new TableColumn<>("Username");
        passwordCol = new TableColumn<>("Password");
        accessLevelCol = new TableColumn<>("AccessLevel");
        firstNameCol = new TableColumn<>("FirstName");
        lastNameCol = new TableColumn<>("LastName");

        employeeIDCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getEmployeeID()));
        usernameCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getUsername()));
        passwordCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getPassword()));
        accessLevelCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getAccessLevel()));
        firstNameCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getFirstName()));
        lastNameCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getLastName()));

        employeeTable.getColumns().setAll(
                employeeIDCol, usernameCol, passwordCol,
                accessLevelCol, firstNameCol, lastNameCol);
    }

    /**
     * Remove all selected Employees upon clicking the Delete button
     */
    @FXML void onDeleteClicked(){
        ObservableList<EmployeeData> selected = this.employeeTable.getSelectionModel().getSelectedItems();
        DatabaseController dbc = new DatabaseController();
        for(EmployeeData employee : selected){
            dbc.removeEmployee(employee.getUsername());
            dbc.commit();
            this.employees.remove(employee);
        }
    }

    @FXML void onCancelClicked(){
        Stage toClose = (Stage)cancelButton.getScene().getWindow();
        toClose.close();
    }
}
