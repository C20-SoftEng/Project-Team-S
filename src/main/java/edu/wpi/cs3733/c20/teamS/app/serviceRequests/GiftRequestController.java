package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.GiftServiceRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GiftRequestController {

    private final PublishSubject<DialogEvent<GiftServiceRequest>> dialogCompleted_ = PublishSubject.create();
    private GiftServiceRequest request = new GiftServiceRequest();
    private Employee loggedIn;

    @FXML
    private Button cancelButton;

    @FXML
    private Button submitButton;

    @FXML
    private TextField locationField;

    @FXML
    private TextField giftRequestField;

    @FXML
    private Label serviceName;


    @FXML void onCancelClicked(ActionEvent event){
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    @FXML void onOKClicked(){
        //request.setGiftType(giftRequestField.getText());
        request.setLocation(locationField.getText());
        request.setMessage(giftRequestField.getText());
        request.assignTo(loggedIn);

        dialogCompleted_.onNext(DialogEvent.ok(request));
    }

    public GiftRequestController(Employee employee) {this.loggedIn = employee;}

    public Observable<DialogEvent<GiftServiceRequest>> dialogCompleted() {
        return dialogCompleted_;
    }
}
