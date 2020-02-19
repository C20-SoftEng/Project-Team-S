package edu.wpi.cs3733.c20.teamS.serviceRequests;

public class FloristServiceRequest extends ServiceRequest {
    private final String implementedBy = "Jordan";
    private String flowerType_;

    public FloristServiceRequest() {
        this(null);
    }
    public FloristServiceRequest(Integer id) {
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }

    public String implementedBy() {return implementedBy; }
    public String flowerType() { return flowerType_; }

    public void setFlowerType_(String flower){
        flowerType_ = flower;
    }
}
