package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class ActiveServiceRequestScreenController implements Initializable {
    @FXML private TableView<ServiceData> serviceRequestTable;
    @FXML private JFXButton cancelButton;
    @FXML private TableColumn<ServiceData, Integer> serviceIDCol;
    @FXML private TableColumn<ServiceData, String> assignedEmployeeCol;
    @FXML private TableColumn<ServiceData, String> statusCol;
    @FXML private TableColumn<ServiceData, String> messageCol;
    @FXML private TableColumn<ServiceData, String> locationCol;
    @FXML private TableColumn<ServiceData, String> typeCol;
    @FXML private JFXButton assignButton;
    @FXML private JFXButton completeButton;

    private ObservableList<ServiceData> activeRequests;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        this.activeRequests = FXCollections.observableArrayList();
        this.update();
        showActiveRequests();
    }


//    public ActiveServiceRequestScreenController(ObservableList<ServiceData> requests){
//        this.serviceRequestTable = new TableView<>();
//        this.activeRequests = requests;
//    }

    public ActiveServiceRequestScreenController(){

    }

    /**
     * show active ServiceRequests in the TableView
     */
    public void showActiveRequests() {
        serviceRequestTable.setItems(this.activeRequests);
        serviceIDCol = new TableColumn<>("Service ID");
        assignedEmployeeCol = new TableColumn<>("Employee Assigned");
        statusCol = new TableColumn<>("Status");
        messageCol = new TableColumn<>("Message");
        locationCol = new TableColumn<>("Location");
        typeCol = new TableColumn<>("Type");

        serviceIDCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getServiceID()));
        //assignedEmployeeCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getAssignedEmployeeID()));
        assignedEmployeeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceData, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceData, String> p) {
               if(p.getValue().getAssignedEmployeeID() == 0){
                   return null;
               }
               return new ReadOnlyObjectWrapper(p.getValue().getAssignedEmployeeID());
            }
        });
        statusCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getStatus()));
        messageCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getMessage()));
        locationCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getServiceNode()));
        typeCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getServiceType()));

        this.serviceRequestTable.setItems(this.activeRequests);
        serviceRequestTable.getColumns().setAll(
                serviceIDCol, assignedEmployeeCol, statusCol,
                messageCol, locationCol, typeCol);
    }

    /**
     * Remove all selected ServiceRequest upon clicking the Complete button
     */
    @FXML void onCompleteClicked(){
//        ObservableList<ServiceData> selected = this.serviceRequestTable.getSelectionModel().getSelectedItems();
//        DatabaseController dbc = new DatabaseController();
//        for(ServiceData service : selected){
//            dbc.deleteServiceWithId(service.getServiceID());
//            dbc.commit();
//            this.activeRequests.remove(service);
//        }

        ServiceData selected = this.serviceRequestTable.getSelectionModel().getSelectedItem();
        DatabaseController dbc = new DatabaseController();
        dbc.deleteServiceWithId(selected.getServiceID());
        dbc.commit();
        this.update();
    }

    /**
     * Open dialog for AssignEmployeeScreen
     */
    @FXML void onAssignClicked(){
        ServiceData selected = this.serviceRequestTable.getSelectionModel().getSelectedItem();
        AssignEmployeeScreen.showDialog(selected, this);
    }


    /**
     * Load existing ServiceData from database and update the screen
     */
    public void update(){
        DatabaseController dbController = new DatabaseController();
        Set<ServiceData> dbServices = dbController.getAllServiceRequestData();
        //ObservableList<ServiceData> activeServices = FXCollections.observableArrayList();
        //activeServices.addAll(dbServices);
        this.activeRequests.removeAll(this.activeRequests);
        this.activeRequests.addAll(dbServices);
        //this.activeRequests = activeServices;
    }


    @FXML void onCancelClicked(){
        Stage toClose = (Stage)cancelButton.getScene().getWindow();
        toClose.close();
    }
}
