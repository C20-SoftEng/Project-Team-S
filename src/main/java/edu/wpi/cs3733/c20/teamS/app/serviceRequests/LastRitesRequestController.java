package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.LastRitesRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LastRitesRequestController {
    private final PublishSubject<DialogEvent<LastRitesRequest>> dialogCompleted_ = PublishSubject.create();
    private LastRitesRequest request = new LastRitesRequest();
    private Employee loggedIn;

    @FXML
    private TextField religionField;
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
        request.setDrugType_(religionField.getText());
        request.setLocation(locationField.getText());
        request.setMessage(commentsField.getText());
        request.assignTo(loggedIn);

        dialogCompleted_.onNext(DialogEvent.ok(request));
    }

    public LastRitesRequestController(Employee employee){
        this.loggedIn = employee;
    }

    public Observable<DialogEvent<LastRitesRequest>> dialogCompleted() {
        return dialogCompleted_;
    }

}
