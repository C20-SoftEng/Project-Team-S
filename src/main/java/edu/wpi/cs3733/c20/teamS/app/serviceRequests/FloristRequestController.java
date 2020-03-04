package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.FloristServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;



public class FloristRequestController {

    private final PublishSubject<DialogEvent<FloristServiceRequest>> dialogCompleted_ = PublishSubject.create();
    private FloristServiceRequest request = new FloristServiceRequest();
    private Employee loggedIn;

    @FXML
    private JFXTextField locationField;

    @FXML
    private JFXTextField commentsField;



    @FXML private JFXTextField flowerRequested;

    @FXML
    private JFXCheckBox Hydrangeas;
    @FXML
    private JFXCheckBox Lilies;
    @FXML
    private JFXCheckBox Roses;
    @FXML
    private JFXCheckBox Daisies;
    @FXML
    private JFXCheckBox Poppies;
    @FXML
    private JFXCheckBox Orchids;

    public FloristRequestController(Employee loggedIn) {
        this.loggedIn = loggedIn;
    }


    @FXML
    void onCancelClicked(ActionEvent event) {
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    @FXML
    void onSubmitClicked(ActionEvent event) {
        if(!flowerRequested.getText().equals("") && !locationField.getText().equals("")) {
            request.setFlowerTypes_(flowerRequested.getText());
            request.setLocation(locationField.getText());
            request.setMessage(commentsField.getText());
            request.assignTo(loggedIn);

            dialogCompleted_.onNext(DialogEvent.ok(request));
        }
    }

    public Observable<DialogEvent<FloristServiceRequest>> dialogCompleted() {
        return dialogCompleted_;
    }
}
