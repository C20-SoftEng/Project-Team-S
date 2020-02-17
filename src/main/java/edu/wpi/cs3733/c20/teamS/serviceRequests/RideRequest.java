package edu.wpi.cs3733.c20.teamS.serviceRequests;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.time.LocalDateTime;

public final class RideRequest extends ServiceRequest {
    private final String person_;
    private final LocalDateTime pickupTime_;

    public RideRequest(String person, LocalDateTime pickupTime) {
        this(null, person, pickupTime);
    }
    public RideRequest(Integer id, String person, LocalDateTime pickupTime) {
        super(id);
        if (person == null) ThrowHelper.illegalNull("person");
        if (pickupTime == null) ThrowHelper.illegalNull("pickupTime");

        this.person_ = person;
        this.pickupTime_ = pickupTime;
    }

    @Override
    public void accept(RequestVisitor visitor) {
        visitor.visit(this);
    }

    public String person() {
        return person_;
    }
    public LocalDateTime pickupTime() {
        return pickupTime_;
    }
}
