package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.ServiceTechServiceRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class ServiceTechRequestController {

    private final PublishSubject<DialogEvent<ServiceTechServiceRequest>> dialogCompleted_ = PublishSubject.create();
    private ServiceTechServiceRequest request = new ServiceTechServiceRequest();
    private Employee loggedIn;

    @FXML
    private TextField locationField;
    @FXML
    private TextField commentsField;
    @FXML
    private JFXButton submitButton;
    @FXML
    private JFXButton cancelButton;

    @FXML void onCancelClicked(ActionEvent event){
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    @FXML void onOKClicked(){
        if(!locationField.getText().equals("")) {
            request.setLocation(locationField.getText());
            request.setMessage(commentsField.getText());
            request.assignTo(loggedIn);

            dialogCompleted_.onNext(DialogEvent.ok(request));
        }
    }

    public ServiceTechRequestController(Employee employee){
        this.loggedIn = employee;
    }

    public Observable<DialogEvent<ServiceTechServiceRequest>> dialogCompleted() {
        return dialogCompleted_;
    }


}
