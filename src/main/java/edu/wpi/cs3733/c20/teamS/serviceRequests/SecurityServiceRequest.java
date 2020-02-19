package edu.wpi.cs3733.c20.teamS.serviceRequests;

public class SecurityServiceRequest extends ServiceRequest {
    private final String implementedBy = "Zeynep Seker";
    private String threatLevel_;
    private String hasWeapon_;

    public SecurityServiceRequest() { this(null); }
    public SecurityServiceRequest(Integer id) {super(id);}

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }

    public String implementedBy() {return implementedBy; }
    public String threatLevel() { return threatLevel_; }

    public void setThreatLevel_(String threatLevel){
        threatLevel_ = threatLevel;
    }
    public void setHasWeapon_(String hasWeapon){ hasWeapon_ = hasWeapon; }

}
