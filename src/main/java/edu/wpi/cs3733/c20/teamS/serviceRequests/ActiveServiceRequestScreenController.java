package edu.wpi.cs3733.c20.teamS.serviceRequests;

import com.jfoenix.controls.JFXButton;
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

import java.util.Set;

public class ActiveServiceRequestScreenController {
    @FXML
    private JFXButton Completed_Button;

    @FXML
    private TableView<ServiceRequest> serviceRequestTable;

    @FXML
    private TableColumn<ServiceRequest, Integer> serviceIDCol;

    @FXML
    private TableColumn<ServiceRequest, String> assignedEmployeeCol;

    @FXML
    private TableColumn<ServiceRequest, String> statusCol;

    @FXML
    private TableColumn<ServiceRequest, String> messageCol;

    @FXML
    private TableColumn<ServiceRequest, String> locationCol;


    @FXML
    private Label Screen_Title;

    private Stage stage;

    private ObservableList<ServiceRequest> activeRequests;

    public ActiveServiceRequestScreenController(){

    }

    public ActiveServiceRequestScreenController(Stage stage, ObservableList<ServiceRequest> requests){
        this.stage = stage;
        this.serviceRequestTable = new TableView<ServiceRequest>();
        this.activeRequests = requests;
    }

    /**
     * show active ServiceRequests in the TableView
     */
    public void showActiveRequests(){
        serviceRequestTable.setItems(this.activeRequests);
        serviceIDCol = new TableColumn<ServiceRequest, Integer>("Service ID");
        assignedEmployeeCol = new TableColumn<ServiceRequest, String>("Employee Assigned");
        statusCol = new TableColumn<ServiceRequest, String>("Status");
        messageCol = new TableColumn<ServiceRequest, String>("Message");
        locationCol = new TableColumn<ServiceRequest, String>("Location");

        serviceIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceRequest, Integer>,
                ObservableValue<Integer>>(){
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ServiceRequest, Integer> p){
                return new ReadOnlyObjectWrapper(p.getValue().id());
            }
        });

        assignedEmployeeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceRequest, String>,
                ObservableValue<String>>(){
           public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceRequest, String> p){
               return new ReadOnlyObjectWrapper(p.getValue().assignee().toString());
           }
        });

        statusCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceRequest, String>,
                ObservableValue<String>>(){
           public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceRequest, String> p){
               return new ReadOnlyObjectWrapper(p.getValue().status().toString());
           }
        });

        messageCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceRequest, String>,
                ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceRequest, String> p){
                return new ReadOnlyObjectWrapper(p.getValue().message());
            }
        });

        locationCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceRequest, String>,
                ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceRequest, String> p){
                return new ReadOnlyObjectWrapper(p.getValue().location());
            }
        });

        serviceRequestTable.getColumns().setAll(serviceIDCol, assignedEmployeeCol, statusCol, messageCol, locationCol);
    }

    public void onCompleteClicked(){

    }
}
