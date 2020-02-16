package edu.wpi.cs3733.c20.teamS.serviceRequests;

public final class JanitorRequest extends ServiceRequest {

    public JanitorRequest() {
        super(null);
    }
    public JanitorRequest(int id) {
        super(id);
    }

    @Override
    public void accept(RequestVisitor visitor) {
        visitor.visit(this);
    }
}
