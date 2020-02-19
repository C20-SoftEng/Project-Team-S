package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class DrugRequestController {

    private final PublishSubject<DialogEvent<DrugServiceRequest>> dialogCompleted_ = PublishSubject.create();
    private DrugServiceRequest request = new DrugServiceRequest();
    private Employee loggedIn;

    @FXML
    private TextField drugField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField commentsField;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;

    @FXML void onCancelClicked(ActionEvent event){
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    @FXML void onOKClicked(){
        request.setDrugType_(drugField.getText());
        request.setLocation(locationField.getText());
        request.setMessage(commentsField.getText());
        request.assignTo(loggedIn);

        dialogCompleted_.onNext(DialogEvent.ok(request));
    }

    public DrugRequestController(Employee employee){
        this.loggedIn = employee;
    }

    public Observable<DialogEvent<DrugServiceRequest>> dialogCompleted() {
        return dialogCompleted_;
    }


}
