package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideServiceRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.scene.control.Button;
import javafx.fxml.FXML;


public class DrugRequestController {

    private final PublishSubject<DialogEvent<DrugServiceRequest>> dialogCompleted_ = PublishSubject.create();

    @FXML
    private Button okButton;

    public Observable<DialogEvent<DrugServiceRequest>> dialogCompleted() {
        return dialogCompleted_;
    }


}
