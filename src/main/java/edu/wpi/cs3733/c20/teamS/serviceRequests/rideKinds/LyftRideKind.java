package edu.wpi.cs3733.c20.teamS.serviceRequests.rideKinds;

import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public final class LyftRideKind extends RideKind {
    private LyftType type_ = LyftType.CLASSIC;
    private double primeTime_ = 0;

    public LyftType type() {
        return type_;
    }
    public void setType(LyftType value) {
        type_ = value;
    }

    @Override
    public String toString() {
        return "Lyft";
    }
}
