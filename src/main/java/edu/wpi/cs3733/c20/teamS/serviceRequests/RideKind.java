package edu.wpi.cs3733.c20.teamS.serviceRequests;

public enum RideKind {
    FRIEND ("Friendly ride", "Rider"),
    SHUTTLE ("Shuttle ride", "Passenger"),
    LYFT ("Lyft ride", "Rider"),
    AMBULANCE("Ambulance ride", "Patient"),
    HELICOPTER("Airlift ride", "Patient"),
    COP_CAR("Ride to jail", "Suspect");

    RideKind(String nicifiedName, String riderTitle) {
        this.nicifiedName = nicifiedName;
        this.riderTitle = riderTitle;
    }

    private final String nicifiedName;
    private final String riderTitle;

    public String nicifiedName() {
        return nicifiedName;
    }
    public String riderTitle() {
        return riderTitle;
    }
}
