package edu.wpi.cs3733.c20.teamS.serviceRequests.rideKinds;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

public final class FriendRideKind extends RideKind {
    private String friendName_;
    private boolean isCool_;

    public FriendRideKind() {}
    public FriendRideKind(String friendName) {
        this.friendName_ = friendName;
    }
    public String friendName() {
        return friendName_;
    }
    public void setFriendName(String value) {
        friendName_ = value;
    }
    public boolean isCool() {
        return isCool_;
    }
    public void setCool(boolean value) {
        isCool_ = value;
    }

    @Override
    public String toString() {
        return "Friendly";
    }
}
