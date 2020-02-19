package edu.wpi.cs3733.c20.teamS.serviceRequests;

import edu.wpi.cs3733.c20.teamS.serviceRequests.RideKind;

import java.time.LocalDateTime;

public final class RideServiceRequest extends ServiceRequest {
    private static final class Members {
        public LocalDateTime pickupTime;
        public String riderName;
        public String destination;
        public RideKind rideKind;
    }
    private Members members = new Members();

    public RideServiceRequest() {
        super();
    }
    public RideServiceRequest(Integer id) {
        this(id, new Members());
    }
    private RideServiceRequest(Integer id, Members members) {
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }

    public LocalDateTime pickupTime() {
        return members.pickupTime;
    }
    public void setPickupTime(LocalDateTime value) {
        members.pickupTime = value;
    }
    public String riderName() {
        return members.riderName;
    }
    public void setRiderName(String value) {
        members.riderName = value;
    }
    public String destination() {
        return members.destination;
    }
    public void setDestination(String value) {
        members.destination = value;
    }
    public RideKind rideKind() {
        return members.rideKind;
    }
    public void setRideKind(RideKind value) {
        members.rideKind = value;
    }
}
