package edu.wpi.cs3733.c20.teamS.serviceRequests;

public class MaintenanceServiceRequest extends ServiceRequest {
    private final String implementedBy = "Keming";
    private String issue;
    private String equipment;

    public MaintenanceServiceRequest(){
        this(null);
    }

    public MaintenanceServiceRequest(Integer id){
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor){
        visitor.visit(this);
    }

    public String issue(){
        return this.issue;
    }

    public String equipment(){
        return this.equipment;
    }

    public String implementedBy(){
        return this.implementedBy;
    }

    public void setIssue(String issue){
        this.issue = issue;
    }

    public void setEquipment(String equipment){
        this.equipment = equipment;
    }
}
