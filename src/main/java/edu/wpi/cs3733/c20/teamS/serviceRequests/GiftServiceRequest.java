package edu.wpi.cs3733.c20.teamS.serviceRequests;

public class GiftServiceRequest extends ServiceRequest {
    private final String implementedBy = "Mario";
    private String giftDesired;

    public GiftServiceRequest() {
        this(null);
    }
    public GiftServiceRequest(Integer id) {
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }

    public String implementedBy() {return implementedBy; }
    public String gift() { return giftDesired; }

    public void setGiftType(String giftDesired){
        this.giftDesired = giftDesired;
    }
}
