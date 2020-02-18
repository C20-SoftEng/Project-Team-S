package edu.wpi.cs3733.c20.teamS.serviceRequests;

public final class JanitorServiceRequest extends ServiceRequest {
    public JanitorServiceRequest() {
        this(null);
    }
    public JanitorServiceRequest(Integer id) {
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }
}
