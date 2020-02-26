package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.InterpreterServiceRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InterpreterRequestController {


    private final PublishSubject<DialogEvent<InterpreterServiceRequest>> dialogCompleted_ = PublishSubject.create();
    private InterpreterServiceRequest request = new InterpreterServiceRequest();
    private Employee loggedIn;

    @FXML
    private Button cancelButton1;
    @FXML
    private Button submitButton1;
    @FXML
    private TextField locationField;
    @FXML
    private TextField commentsField;
    @FXML
    private Label serviceName;

    @FXML private RadioButton EnglishButton;
    @FXML private RadioButton EspanolButton;
    @FXML private RadioButton ElbonianButton;

    @FXML private ToggleGroup One;



    @FXML void onCancelClicked(ActionEvent event){
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    @FXML void onOKClicked(){
        if(!locationField.getText().equals("")) {
            request.setInterpreterType_(((RadioButton) One.getSelectedToggle()).getText());
            request.setLocation(locationField.getText());
            request.setMessage(commentsField.getText());
            request.assignTo(this.loggedIn);

            dialogCompleted_.onNext(DialogEvent.ok(request));
        }
    }

    public InterpreterRequestController(Employee employee){
        this.loggedIn = employee;
    }

    public Observable<DialogEvent<InterpreterServiceRequest>> dialogCompleted() {
        return dialogCompleted_;
    }


}
