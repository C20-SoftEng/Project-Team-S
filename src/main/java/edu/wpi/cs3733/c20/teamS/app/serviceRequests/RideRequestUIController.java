package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideKind;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.util.ResourceBundle;

public final class RideRequestUIController implements Initializable {
    private final PublishSubject<DialogEvent<RideServiceRequest>> dialogCompleted_ = PublishSubject.create();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rideKindSelector.setFriendRide();
    }

    private class RideKindSelector {
        private RideKind current;

        public RideKind current() {
            return current;
        }
        private void setCurrent(RideKind value) {
            current = value;
            switch (current) {
                case FRIEND:
                case LYFT:
                case SHUTTLE:
                    riderNameLabel.setText("Rider Name:");
                    break;
                case AMBULANCE:
                case HELICOPTER:
                    riderNameLabel.setText("Patient Name:");
                    break;
                case COP_CAR:
                    riderNameLabel.setText("Suspect Name:");
                    break;
                default:
                    throw new IllegalStateException("Unexpected RideKind value in switch statement.");
            }

            RideRequestUIController.this.rideKindLabel.setText(current.toString());
        }
        public void setLyftRide() {
            setCurrent(RideKind.LYFT);
        }
        public void setFriendRide() {
            setCurrent(RideKind.FRIEND);
        }
        public void setShuttleRide() {
            setCurrent(RideKind.SHUTTLE);
        }
        public void setAmbulanceRide() {
            setCurrent(RideKind.AMBULANCE);
        }
        public void setHeliRide() {
            setCurrent(RideKind.HELICOPTER);
        }
        public void setCopCarRide() {
            setCurrent(RideKind.COP_CAR);
        }
    }
    private final RideKindSelector rideKindSelector = new RideKindSelector();

    @FXML private Label riderNameLabel;
    @FXML private JFXTextField riderNameField;
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
    @FXML private void onAmbulanceClicked() {
        rideKindSelector.setAmbulanceRide();
    }
    @FXML private void onHeliClicked() {
        rideKindSelector.setHeliRide();
    }
    @FXML private void onCopCarClicked() {
        rideKindSelector.setCopCarRide();
    }
    @FXML private void onOKClicked() {
        RideServiceRequest request = new RideServiceRequest();
        request.setPickupTime(pickupTimePicker.getDateTimeValue());
        request.setRiderName(riderNameField.getText());
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
