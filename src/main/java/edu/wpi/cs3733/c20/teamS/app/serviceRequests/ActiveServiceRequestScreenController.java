package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ActiveServiceRequestScreenController {
    @FXML private TableView<ServiceData> serviceRequestTable;
    @FXML private JFXButton cancelButton;
    @FXML private TableColumn<ServiceData, Integer> serviceIDCol;
    @FXML private TableColumn<ServiceData, String> assignedEmployeeCol;
    @FXML private TableColumn<ServiceData, String> statusCol;
    @FXML private TableColumn<ServiceData, String> messageCol;
    @FXML private TableColumn<ServiceData, String> locationCol;
    @FXML private TableColumn<ServiceData, String> typeCol;

    private ObservableList<ServiceData> activeRequests;

    @FXML
    public void initialize() {
        showActiveRequests();
    }

    public ActiveServiceRequestScreenController(ObservableList<ServiceData> requests){
        this.serviceRequestTable = new TableView<>();
        this.activeRequests = requests;
    }

    /**
     * show active ServiceRequests in the TableView
     */
    public void showActiveRequests(){
        serviceRequestTable.setItems(this.activeRequests);
        serviceIDCol = new TableColumn<>("Service ID");
        assignedEmployeeCol = new TableColumn<>("Employee Assigned");
        statusCol = new TableColumn<>("Status");
        messageCol = new TableColumn<>("Message");
        locationCol = new TableColumn<>("Location");
        typeCol = new TableColumn<>("Type");

        serviceIDCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getServiceID()));
        assignedEmployeeCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getAssignedEmployeeID()));
        statusCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getStatus()));
        messageCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getMessage()));
        locationCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getServiceNode()));
        typeCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getServiceType()));

        serviceRequestTable.getColumns().setAll(
                serviceIDCol, assignedEmployeeCol, statusCol,
                messageCol, locationCol, typeCol);
    }

    /**
     * Remove all selected ServiceRequest upon clicking the Complete button
     */
    @FXML void onCompleteClicked(){
        ObservableList<ServiceData> selected = this.serviceRequestTable.getSelectionModel().getSelectedItems();
        DatabaseController dbc = new DatabaseController();
        for(ServiceData service : selected){
            dbc.deleteServiceWithId(service.getServiceID());
            dbc.commit();
            this.activeRequests.remove(service);
        }
    }

    @FXML void onCancelClicked(){
        Stage toClose = (Stage)cancelButton.getScene().getWindow();
        toClose.close();
    }
}
