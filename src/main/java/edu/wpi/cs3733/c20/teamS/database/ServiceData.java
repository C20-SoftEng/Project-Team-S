package edu.wpi.cs3733.c20.teamS.database;


import java.sql.Date;

public class ServiceData{
    private int serviceID;
    private String serviceType;
    private String status;
    private String message;
    private String data;
    private int assignedEmployeeID;
    private Date date;
    private String serviceNode;

    public String getServiceNode() {
        return serviceNode;
    }

    public void setServiceNode(String serviceNode) {
        this.serviceNode = serviceNode;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ServiceData{" +
                "serviceID=" + serviceID +
                ", serviceType='" + serviceType + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", assignedEmployeeID=" + assignedEmployeeID +
                ", date=" + date +
                ", serviceNode='" + serviceNode + '\'' +
                '}';
    }

    public ServiceData(
            int serviceID, String serviceType,
            String status, String message, String data,
            int assignedEmployeeID,
            Date date, String serviceNode) {
        this.serviceID = serviceID;
        this.serviceType = serviceType;
        this.status = status;
        this.message = message;
        this.data = data;
        this.assignedEmployeeID = assignedEmployeeID;
        this.date = date;
        this.serviceNode = serviceNode;
    }

    public ServiceData(String serviceType, String status, String message, String data, int assignedEmployeeID, String serviceNode) {
        this.serviceID = 0;
        this.serviceType = serviceType;
        this.status = status;
        this.message = message;
        this.data = data;
        this.assignedEmployeeID = assignedEmployeeID;
        this.date = null;
        this.serviceNode = serviceNode;
    }


    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAssignedEmployeeID() {
        return assignedEmployeeID;
    }

    public void setAssignedEmployeeID(int assignedEmployeeID) {
        this.assignedEmployeeID = assignedEmployeeID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }








}
