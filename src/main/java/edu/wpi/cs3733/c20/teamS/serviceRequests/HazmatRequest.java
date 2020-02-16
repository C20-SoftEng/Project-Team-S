package edu.wpi.cs3733.c20.teamS.serviceRequests;

public final class HazmatRequest extends ServiceRequest {

    public HazmatRequest() {
        super(null);
    }
    public HazmatRequest(int id) {
        super(id);
    }
    @Override
    public void accept(RequestVisitor visitor) {
        visitor.visit(this);
    }
}
