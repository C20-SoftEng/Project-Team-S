package edu.wpi.cs3733.c20.teamS.serviceRequests;

public class LaundryServiceRequest extends ServiceRequest {
    private final String implementedBy = "Tommy";

    public LaundryServiceRequest() {
        this(null);
    }
    public LaundryServiceRequest(Integer id) {
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }

    public String implementedBy() {return implementedBy; }

}
