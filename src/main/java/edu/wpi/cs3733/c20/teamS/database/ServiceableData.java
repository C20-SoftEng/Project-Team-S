package edu.wpi.cs3733.c20.teamS.database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ServiceableData {
    private SimpleIntegerProperty employeeID;
    private SimpleStringProperty serviceType;

    public ServiceableData(int employeeID, String serviceType){
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.serviceType = new SimpleStringProperty(serviceType);
    }

    public int getEmployeeID(){
        return this.employeeID.get();
    }

    public String getServiceType(){
        return this.serviceType.get();
    }

    public void setEmployeeID(int employeeID){
        this.employeeID.set(employeeID);
    }

    public void setServiceType(String serviceType){
        this.serviceType.set(serviceType);
    }

}
