package edu.wpi.cs3733.c20.teamS.serviceRequests;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;

public final class RideServiceRequest extends ServiceRequest {
    private LocalDateTime pickupTime_;
    private String riderName_;
    private String destination_;
    private RideKind rideKind_;

    public RideServiceRequest() {
        super();
    }
    public RideServiceRequest(Integer id) {
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }

    public LocalDateTime pickupTime() {
        return pickupTime_;
    }
    public void setPickupTime(LocalDateTime value) {
        pickupTime_ = value;
    }
    public String riderName() {
        return riderName_;
    }
    public void setRiderName(String value) {
        riderName_ = value;
    }
    public String destination() {
        return destination_;
    }
    public void setDestination(String value) {
        destination_ = value;
    }
    public RideKind rideKind() {
        return rideKind_;
    }
    public void setRideKind(RideKind value) {
        rideKind_ = value;
    }
}
