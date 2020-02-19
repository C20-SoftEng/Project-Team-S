package edu.wpi.cs3733.c20.teamS.serviceRequests;

import java.util.LinkedList;

public class FloristServiceRequest extends ServiceRequest {
    private final String implementedBy = "Jordan";
    private String flowerTypes_;
    private String numFlowers;

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

    public String implementedBy() {
        return implementedBy;
    }

    public void setFlowerTypes_(String flowers) {
        flowerTypes_ = flowers;
    }

    public void setNumFlowers_(String flowers) {
        numFlowers = flowers;
    }

}

