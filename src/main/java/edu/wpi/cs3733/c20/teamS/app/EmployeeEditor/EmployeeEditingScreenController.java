package edu.wpi.cs3733.c20.teamS.app.EmployeeEditor;

import com.jfoenix.controls.JFXButton;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeEditingScreenController implements Initializable{
    @FXML private TableView<EmployeeData> employeeTable;
    @FXML private JFXButton cancelButton;
    @FXML private JFXButton showCapabilitiesButton;
    @FXML private TableColumn<EmployeeData, Integer> employeeIDCol;
    @FXML private TableColumn<EmployeeData, String> usernameCol;
    @FXML private TableColumn<EmployeeData, String> passwordCol;
    @FXML private TableColumn<EmployeeData, Integer> accessLevelCol;
    @FXML private TableColumn<EmployeeData, String> firstNameCol;
    @FXML private TableColumn<EmployeeData, String> lastNameCol;

    private ObservableList<EmployeeData> employees;

//    @FXML
//    public void initialize() {
//        showEmployees();
//        System.out.println("Controller Initialized");
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.employees = FXCollections.observableArrayList();
        this.update();
        DatabaseController dbController = new DatabaseController();

        employeeIDCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, Integer>("EmployeeID"));

        usernameCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("Username"));

        /*
        usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        usernameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeData, String> t) ->
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setUsername(t.getNewValue())

                );

         */

        passwordCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("Password"));
        passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeData, String> t) -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setPassword(t.getNewValue());
                    dbController.updateEmployee((t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ));
                });


        accessLevelCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, Integer>("AccessLevel"));
        accessLevelCol.setCellFactory(TextFieldTableCell.<EmployeeData, Integer>forTableColumn(
                (new IntegerStringConverter())));
        accessLevelCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeData, Integer> t) -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAccessLevel(t.getNewValue().intValue());
                    dbController.updateEmployee( (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ));
                });


        firstNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("FirstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeData, String> t) -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setFirstName(t.getNewValue());
                    dbController.updateEmployee((t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ));
                });

        lastNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("LastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeData, String> t) -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setLastName(t.getNewValue());
                    dbController.updateEmployee((t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ));
                });

        employeeTable.setItems(employees);
    }

//    public EmployeeEditingScreenController(ObservableList<EmployeeData> employees){
//        //this.employeeTable = new TableView<>();
//        //this.employeeTable.setEditable(true);
//        this.employees = employees;
//    }

    public EmployeeEditingScreenController(){
        //this.employeeTable = new TableView<>();
        //this.employeeTable.setEditable(true);
        //this.employees = employees;
    }


//    /**
//     * show Employees in the TableView
//     */
//    public void showEmployees(){
//        //employeeTable.setItems(this.employees);
//        employeeIDCol = new TableColumn<EmployeeData, Integer>("Employee ID");
//        usernameCol = new TableColumn<EmployeeData, String>("Username");
//        passwordCol = new TableColumn<EmployeeData, String>("Password");
//        accessLevelCol = new TableColumn<EmployeeData, Integer>("AccessLevel");
//        firstNameCol = new TableColumn<EmployeeData, String>("FirstName");
//        lastNameCol = new TableColumn<EmployeeData, String>("LastName");
//
//
////        employeeIDCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getEmployeeID()));
////        usernameCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getUsername()));
////        passwordCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getPassword()));
////        accessLevelCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getAccessLevel()));
////        firstNameCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getFirstName()));
////        lastNameCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getLastName()));
//
//
//        employeeIDCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, Integer>("EmployeeID"));
//
//        usernameCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("Username"));
//        usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        /*
//        usernameCol.setOnEditCommit(
//                (EventHandler<CellEditEvent<EmployeeData, String>>) t -> ((EmployeeData) t.getTableView().getItems().get(
//                        t.getTablePosition().getRow())
//                ).setUsername(t.getNewValue())
//        );
//         */
//        usernameCol.setOnEditCommit(
//                (TableColumn.CellEditEvent<EmployeeData, String> t) ->
//                        (t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setUsername(t.getNewValue())
//
//                );
//
//        passwordCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("Password"));
//        accessLevelCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, Integer>("AccessLevel"));
//        firstNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("FirstName"));
//        lastNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("LastName"));
//
//
//        employeeTable.setItems(this.employees);
//        employeeTable.getColumns().setAll(
//                employeeIDCol, usernameCol, passwordCol,
//                accessLevelCol, firstNameCol, lastNameCol);
//    }

    /**
     * Remove all selected Employees upon clicking the Delete button
     */
    @FXML void onDeleteClicked(){
        ObservableList<EmployeeData> selected = this.employeeTable.getSelectionModel().getSelectedItems();
        DatabaseController dbc = new DatabaseController();
        for(EmployeeData employee : selected){
            dbc.removeEmployeeCapabilities(employee.getEmployeeID());
            dbc.removeEmployee(employee.getUsername());
            dbc.commit();
            this.update();
        }
    }

    @FXML void onAddClicked(){
        AddEmployeeScreen.showDialog(this);
    }

    @FXML void onCancelClicked(){
        Stage toClose = (Stage)cancelButton.getScene().getWindow();
        toClose.close();
    }


//    public void update(EmployeeData ed){
//        this.employees.add(ed);
//    }

    public void update(){
        DatabaseController dbController = new DatabaseController();
        this.employees.removeAll(this.employees);
        this.employees.addAll(dbController.getAllEmployeeData());
    }

    @FXML void onShowCapabilitiesClicked(){
        EmployeeData selected = this.employeeTable.getSelectionModel().getSelectedItem();
        CapabilityEditingScreen.showDialog(selected);
    }
}
