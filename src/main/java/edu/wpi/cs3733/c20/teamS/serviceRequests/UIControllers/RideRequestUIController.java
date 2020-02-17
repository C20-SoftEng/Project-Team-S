package edu.wpi.cs3733.c20.teamS.serviceRequests.UIControllers;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXML;
import tornadofx.control.DateTimePicker;

public class RideRequestUIController {
    private final PublishSubject<RideRequest> completed_ = PublishSubject.create();
    private final PublishSubject<Object> canceled_ = PublishSubject.create();
    @FXML private DateTimePicker rideTimePicker;
    @FXML private JFXTextField personNameTextField;
    @FXML private void onOKClicked() {
        completed_.onNext(createRideRequestFromInput());
    }
    @FXML private void onCancelClicked() {
        canceled_.onNext(this);
    }

    /**
     * Raised when the dialog is completed affirmatively by the user.
     */
    public Observable<RideRequest> completed() {
        return completed_;
    }

    /**
     * Raised when dialog is completed negatively by the user.
     */
    public Observable<Object> canceled() {
        return canceled_;
    }

    private RideRequest createRideRequestFromInput() {
        RideRequest result = new RideRequest(
                personNameTextField.getText(),
                rideTimePicker.getDateTimeValue()
        );

        return result;
    }
}
