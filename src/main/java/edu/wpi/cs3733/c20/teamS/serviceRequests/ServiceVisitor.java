package edu.wpi.cs3733.c20.teamS.serviceRequests;

public abstract class ServiceVisitor {
    public abstract void visit(JanitorServiceRequest request);
    public abstract void visit(RideServiceRequest request);
}
