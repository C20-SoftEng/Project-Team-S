package edu.wpi.cs3733.c20.teamS.serviceRequests;

public class ServiceTechServiceRequest extends ServiceRequest{
    private final String implementedBy = "Marc";

    public ServiceTechServiceRequest() {
        this(null);
    }
    public ServiceTechServiceRequest(Integer id) {
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }

    public String implementedBy() {return implementedBy; }
}
