package edu.wpi.cs3733.c20.teamS.serviceRequests;

public class DrugServiceRequest extends ServiceRequest {
    private final String implementedBy = "Owen";
    private String drugType_;

    public DrugServiceRequest() {
        this(null);
    }
    public DrugServiceRequest(Integer id) {
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }

    public String implementedBy() {return implementedBy; }
    public String drugType() { return drugType_; }

    public void setDrugType_(String drug){
        drugType_ = drug;
    }
}
