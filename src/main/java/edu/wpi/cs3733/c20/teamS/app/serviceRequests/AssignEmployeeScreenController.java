package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AssignEmployeeScreenController implements Initializable{
    @FXML private TextField assigneeID;
    @FXML private TextField assigneeFirstName;
    @FXML private TextField assigneeLastName;
    @FXML private JFXButton assignButton;
    @FXML private TableView<EmployeeData> capEmployeeTable;
    @FXML private TableColumn<EmployeeData, Integer> capEmployeeIDCol;
    @FXML private TableColumn<EmployeeData, String> capEmployeeFirstNameCol;
    @FXML private TableColumn<EmployeeData, String> capEmployeeLastNameCol;

    private ObservableList<EmployeeData> capEmployees;
    private ServiceData tbAssigned;
    private ActiveServiceRequestScreenController cont;

    @FXML
    public void initialize(URL location, ResourceBundle resources){
        this.capEmployees = FXCollections.observableArrayList();
        this.update();

        capEmployeeIDCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, Integer>("EmployeeID"));
        capEmployeeFirstNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("FirstName"));
        capEmployeeLastNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("LastName"));

        capEmployeeTable.setItems(capEmployees);
    }

    public AssignEmployeeScreenController(ServiceData tbAssigned, ActiveServiceRequestScreenController controller){
        this.tbAssigned = tbAssigned;
        this.cont = controller;
    }

    @FXML void onAssignClicked(){
        EmployeeData selected = this.capEmployeeTable.getSelectionModel().getSelectedItem();
        this.tbAssigned.setStatus("ASSIGNED");
        this.tbAssigned.setAssignedEmployeeID(selected.getEmployeeID());
        DatabaseController dbc = new DatabaseController();
        dbc.updateServiceData(this.tbAssigned);
        this.update();
        this.cont.update();
    }

    public void update(){
        DatabaseController dbc = new DatabaseController();
        Set<EmployeeData> allEmployees = dbc.getAllEmployeeData();
        Set<EmployeeData> capEmployees = new HashSet<>();
        for(EmployeeData em : allEmployees){
            if(dbc.checkCapable(em.getEmployeeID(), this.tbAssigned.getServiceType())){
                capEmployees.add(em);
            }
        }
        this.capEmployees.removeAll(this.capEmployees);
        this.capEmployees.addAll(capEmployees);
        EmployeeData currAssignee = null;
        for(EmployeeData em : this.capEmployees){
            if(em.getEmployeeID() == (this.tbAssigned.getAssignedEmployeeID())){
                currAssignee = em;
                break;
            }
        }

        this.capEmployees.removeAll(currAssignee);
        this.assigneeID.setText(Integer.toString(currAssignee.getEmployeeID()));
        this.assigneeFirstName.setText(currAssignee.getFirstName());
        this.assigneeLastName.setText(currAssignee.getLastName());
    }


}
