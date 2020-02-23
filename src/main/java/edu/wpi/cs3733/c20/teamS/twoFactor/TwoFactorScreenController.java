package edu.wpi.cs3733.c20.teamS.twoFactor;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.AccessLevel;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

public class TwoFactorScreenController {

    @FXML
    private Label twoFactorLabel;

    @FXML
    private JFXPasswordField tfaCodeField;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton enterButton;

    @FXML
    private Label descLabel;

    @FXML
    private MenuButton carrierSelector;

    @FXML
    private JFXButton sendButton;

    public EmployeeData ed;

    @FXML
    void onCancelClicked(ActionEvent event) {

    }

    @FXML
    void onVerizonClicked(ActionEvent event) {
        carrierSelector.setText("Verizon");
    }

    @FXML
    void OnSprintClicked(ActionEvent event) {
        carrierSelector.setText("Sprint");
    }

    @FXML
    void OnTMobileClicked(ActionEvent event) {
        carrierSelector.setText("T-Mobile");
    }

    @FXML
    void onATTClicked(ActionEvent event) {
        carrierSelector.setText("AT&T");
    }

    @FXML
    void onEnterClicked(ActionEvent event) {
        DatabaseController dbc = new DatabaseController();
        Employee emp = new Employee(ed.getEmployeeID(), ed.getFirstName()+ " " + ed.getLastName(), Employee.toAccess(ed.getAccessLevel()));
        dialogCompleted_.onNext(DialogEvent.ok(emp));
//        DatabaseController dc = new DatabaseController();
//        EmployeeData ed = dc.getEmployee(id.getText());
//        AccessLevel[] al = AccessLevel.values();
//        AccessLevel accessLevel = al[ed.getAccessLevel()];
//        Employee emp = new Employee(ed.getEmployeeID(), ed.getFirstName()+ " " + ed.getLastName(), accessLevel);
//        dialogCompleted_.onNext(DialogEvent.ok(emp));

    }

    @FXML
    void onSendClicked(ActionEvent event) {

        if(carrierSelector.getText()=="Carrier"){
            return;
        }

        sendButton.setDisable(true);
        String carrier = carrierSelector.getText();
        carrierSelector.setDisable(true);
        descLabel.setText("Text Sent");



    }

    public PublishSubject<DialogEvent<Employee>> dialogCompleted_;
    public Observable<DialogEvent<Employee>> dialogCompleted() {
        return dialogCompleted_;
    }
    //private final PublishSubject<DialogEvent<Employee>> dialogCompleted_ = PublishSubject.create();

}

