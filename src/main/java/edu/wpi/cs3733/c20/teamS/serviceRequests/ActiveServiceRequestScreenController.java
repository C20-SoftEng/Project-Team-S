package edu.wpi.cs3733.c20.teamS.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.MapZoomer;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class ActiveServiceRequestScreenController {
    @FXML
    private JFXButton completeButton;

    @FXML
    private TableView<ServiceData> serviceRequestTable;

    @FXML
    private TableColumn<ServiceData, Integer> serviceIDCol;

    @FXML
    private TableColumn<ServiceData, String> assignedEmployeeCol;

    @FXML
    private TableColumn<ServiceData, String> statusCol;

    @FXML
    private TableColumn<ServiceData, String> messageCol;

    @FXML
    private TableColumn<ServiceData, String> locationCol;


    @FXML
    private Label Screen_Title;

    //private Stage stage;

    private ObservableList<ServiceData> activeRequests;

    @FXML
    public void initialize() {
        showActiveRequests();
    }

    public ActiveServiceRequestScreenController(ObservableList<ServiceData> requests){
        //this.stage = stage;
        this.serviceRequestTable = new TableView<ServiceData>();
        this.activeRequests = requests;

    }

    /**
     * show active ServiceRequests in the TableView
     */
    public void showActiveRequests(){
        serviceRequestTable.setItems(this.activeRequests);
        serviceIDCol = new TableColumn<ServiceData, Integer>("Service ID");
        assignedEmployeeCol = new TableColumn<ServiceData, String>("Employee Assigned");
        statusCol = new TableColumn<ServiceData, String>("Status");
        messageCol = new TableColumn<ServiceData, String>("Message");
        locationCol = new TableColumn<ServiceData, String>("Location");

        serviceIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceData, Integer>,
                ObservableValue<Integer>>(){
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ServiceData, Integer> p){
                return new ReadOnlyObjectWrapper(p.getValue().getServiceID());
            }
        });

        assignedEmployeeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceData, String>,
                ObservableValue<String>>(){
           public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceData, String> p){
               return new ReadOnlyObjectWrapper(p.getValue().getAssignedEmployeeID());
           }
        });

        statusCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceData, String>,
                ObservableValue<String>>(){
           public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceData, String> p){
               return new ReadOnlyObjectWrapper(p.getValue().getStatus());
           }
        });

        messageCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceData, String>,
                ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceData, String> p){
                return new ReadOnlyObjectWrapper(p.getValue().getMessage());
            }
        });

        locationCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceData, String>,
                ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceData, String> p){
                return new ReadOnlyObjectWrapper(p.getValue().getServiceNode());
            }
        });

        serviceRequestTable.getColumns().setAll(serviceIDCol, assignedEmployeeCol, statusCol, messageCol, locationCol);
    }

    /**
     * Remove all selected ServiceRequest upon clicking the Complete button
     */
    @FXML void onCompleteClicked(){
        ObservableList<ServiceData> selected = this.serviceRequestTable.getSelectionModel().getSelectedItems();
        DatabaseController dbc = new DatabaseController();
        for(ServiceData service : selected){
            dbc.updateServiceData(new ServiceData(service.getServiceID(),service.getServiceType(),"COMPLETE",service.getMessage(),service.getData(),service.getAssignedEmployeeID(),service.getServiceNode()));
            dbc.commit();
            this.activeRequests.remove(service);

        }
    }
}
