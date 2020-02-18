package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.rideKinds.FriendRideKind;
import edu.wpi.cs3733.c20.teamS.serviceRequests.rideKinds.LyftRideKind;
import edu.wpi.cs3733.c20.teamS.serviceRequests.rideKinds.RideKind;
import edu.wpi.cs3733.c20.teamS.serviceRequests.rideKinds.ShuttleRideKind;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tornadofx.control.DateTimePicker;

public class RideRequestUIController {
    private final PublishSubject<DialogEvent<RideServiceRequest>> dialogCompleted_ = PublishSubject.create();
    private class RideKindSelector {
        private final LyftRideKind lyftRide = new LyftRideKind();
        private final FriendRideKind friendRide = new FriendRideKind();
        private final ShuttleRideKind shuttleRide = new ShuttleRideKind();
        private RideKind current;

        public RideKind current() {
            return current;
        }
        private void setCurrent(RideKind value) {
            current = value;
            RideRequestUIController.this.rideKindLabel.setText(current.toString());
        }
        public void setLyftRide() {
            setCurrent(lyftRide);
        }
        public void setFriendRide() {
            setCurrent(friendRide);
        }
        public void setShuttleRide() {
            setCurrent(shuttleRide);
        }
    }
    private final RideKindSelector rideKindSelector = new RideKindSelector();

    @FXML private JFXTextField riderField;
    @FXML private DateTimePicker pickupTimePicker;
    @FXML private JFXTextField destinationField;
    @FXML private Label rideKindLabel;

    @FXML private void onLyftClicked() {
        rideKindSelector.setLyftRide();
    }
    @FXML private void onFriendClicked() {
        rideKindSelector.setFriendRide();
    }
    @FXML private void onShuttleClicked() {
        rideKindSelector.setShuttleRide();
    }
    @FXML private void onOKClicked() {
        RideServiceRequest request = new RideServiceRequest();
        request.setPickupTime(pickupTimePicker.getDateTimeValue());
        request.setRiderName(riderField.getText());
        request.setDestination(destinationField.getText());
        request.setRideKind(rideKindSelector.current());

        dialogCompleted_.onNext(DialogEvent.ok(request));
    }
    @FXML private void onCancelClicked() {
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    public Observable<DialogEvent<RideServiceRequest>> dialogCompleted() {
        return dialogCompleted_;
    }
}
