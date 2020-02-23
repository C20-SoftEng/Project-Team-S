package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.MaintenanceServiceRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class MaintenanceServiceRequestController {
    private final PublishSubject<DialogEvent<MaintenanceServiceRequest>> dialogCompleted_ = PublishSubject.create();
    private MaintenanceServiceRequest request = new MaintenanceServiceRequest();
    private Employee loggedIn;

    @FXML
    private JFXButton submitButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private TextField locationField;

    @FXML
    private TextField commentsField;

    @FXML
    private TextField issueField;

    @FXML
    private TextField equipmentField;

    @FXML void onCancelClicked(){
        this.dialogCompleted_.onNext(DialogEvent.cancel());
    }

    @FXML void onSubmitClicked(){
        if(!issueField.getText().equals("") && !locationField.getText().equals("") && !equipmentField.getText().equals("")) {
            this.request.setLocation(locationField.getText());
            this.request.setMessage(commentsField.getText());
            this.request.setIssue(issueField.getText());
            this.request.setEquipment(equipmentField.getText());
            this.request.assignTo(this.loggedIn);

            dialogCompleted_.onNext(DialogEvent.ok(this.request));
        }
    }

    public MaintenanceServiceRequestController(Employee employee){
        this.loggedIn = employee;
    }

    public Observable<DialogEvent<MaintenanceServiceRequest>> dialogCompleted(){
        return this.dialogCompleted_;
    }



}
