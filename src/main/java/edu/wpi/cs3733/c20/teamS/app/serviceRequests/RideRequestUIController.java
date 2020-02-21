package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideKind;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.util.ResourceBundle;

public final class RideRequestUIController implements Initializable {
    private final PublishSubject<DialogEvent<RideServiceRequest>> dialogCompleted_ = PublishSubject.create();
    private Employee loggedIn;

    public RideRequestUIController(Employee employee){
        this.loggedIn = employee;
    }

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
            riderNameLabel.setText(value.riderTitle());
            switch (current) {
                case FRIEND:
                    rideKindImageView.setImage(friendLargeIcon);
                    break;
                case LYFT:
                    rideKindImageView.setImage(lyftLargeIcon);
                    break;
                case HELICOPTER:
                    rideKindImageView.setImage(heliLargeIcon);
                    break;
                case COP_CAR:
                    rideKindImageView.setImage(copCarLargeIcon);
                    break;
                case AMBULANCE:
                    rideKindImageView.setImage(ambulanceLargeIcon);
                    break;
                case SHUTTLE:
                    rideKindImageView.setImage(shuttleLargeIcon);
                    break;
                default:
                    throw new IllegalStateException("Unexpected RideKind in Switch statement.");
            }
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
    @FXML private ImageView rideKindImageView;

    private final Image friendLargeIcon = new Image("images/Icons/serviceRequests/bestFriends-large.jpm.jpg");
    private final Image lyftLargeIcon = new Image("images/Icons/serviceRequests/lyft-large.png");
    private final Image heliLargeIcon = new Image("images/Icons/serviceRequests/helicopter-large.png");
    private final Image copCarLargeIcon = new Image("images/Icons/serviceRequests/handcuffs-large.png");
    private final Image shuttleLargeIcon = new Image("images/Icons/serviceRequests/shuttle-large.png");
    private final Image ambulanceLargeIcon = new Image("images/Icons/serviceRequests/ambulance-large.png");

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
        request.assignTo(loggedIn);
        request.setLocation("Valet Parking");
        request.setMessage("\"" + request.riderName() + "\" needs a ride.");

        dialogCompleted_.onNext(DialogEvent.ok(request));
    }
    @FXML private void onCancelClicked() {
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    public Observable<DialogEvent<RideServiceRequest>> dialogCompleted() {
        return dialogCompleted_;
    }
}