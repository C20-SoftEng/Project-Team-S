package edu.wpi.cs3733.c20.teamS.serviceRequests;

public class LastRitesRequest extends ServiceRequest{
    private final String implementedBy = "Amy";
    private String religionType_;

    public LastRitesRequest() {
        this(null);
    }
    public LastRitesRequest(Integer id) {
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }

    public String implementedBy() {return implementedBy; }
    public String religionType() { return religionType_; }

    public void setDrugType_(String religionType){
        religionType_ = religionType;
    }
}
