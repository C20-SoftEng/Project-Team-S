package edu.wpi.cs3733.c20.teamS.serviceRequests;

import java.util.LinkedList;

public class FloristServiceRequest extends ServiceRequest {
    private final String implementedBy = "Jordan";
    private String flowerTypes_;
    private int numFlowers;
    private LinkedList<String> flowers;

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

    public String stringifyThis(LinkedList<String> flowers){
        int i;
        int listSize = flowers.size();
        String flowerNames = "";
        for (i = 0; i < listSize; i++){
            flowerNames += " " + flowers.get(i);
        }
        return flowerNames;
    }

    public String flowerType() {
        return flowerTypes_;
    }

    public int numFlowers() {
        return numFlowers;
    }

    public void setFlowerTypes_(LinkedList<String> flowers) {
        flowerTypes_ = stringifyThis(flowers);
    }

    public void setNumFlowers_(int flowers) {
        numFlowers = flowers;
    }
    public LinkedList<String> getFlowers(){
        return flowers;
    }

    public String getStringVer() {
        return this.toString();
    }
}

