package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.SecurityServiceRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


public class SecurityServiceController {

    private final PublishSubject<DialogEvent<SecurityServiceRequest>> dialogCompleted_ = PublishSubject.create();
    private SecurityServiceRequest request = new SecurityServiceRequest();
    private Employee loggedIn;


    @FXML
    private JFXTextField weaponthreat;
    @FXML
    private JFXTextField locationField;
    @FXML
    private JFXTextField commentsField;
    @FXML
    private JFXButton submitButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField threatlevel;

    @FXML void onCancelClicked(ActionEvent event){
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    @FXML void onOKClicked() {
        if (!threatlevel.getText().equals("") && !weaponthreat.getText().equals("") && !locationField.getText().equals("")) {
            request.setThreatLevel_(threatlevel.getText());
            request.setHasWeapon_(weaponthreat.getText());

            request.setLocation(locationField.getText());
            request.setMessage(commentsField.getText());
            request.assignTo(loggedIn);

            dialogCompleted_.onNext(DialogEvent.ok(request));
        }
    }

    public SecurityServiceController(Employee employee){
        this.loggedIn = employee;
    }

    public Observable<DialogEvent<SecurityServiceRequest>> dialogCompleted() {
        return dialogCompleted_;
    }


}
