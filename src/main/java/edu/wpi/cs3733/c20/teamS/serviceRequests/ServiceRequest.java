package edu.wpi.cs3733.c20.teamS.serviceRequests;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

/**
 * A Service request
 */
public final class ServiceRequest {
    private ServiceKind serviceKind_;
    private String comments_;
    private int employeeCreatedID_;
    private int employeeAssignedID_;
    private int serviceID_;
    private String location_;
    private String status_;

    /**
     * Constructor for ServiceRequest
     * @param serviceKind the type of service
     * @param comments the comments defined by the employee
     * @param emp the employee that created
     * @param serviceID the serviceID created by the database
     * @param location the location defined by the employee
     */
    public ServiceRequest(ServiceKind serviceKind, String comments, int emp, int serviceID, String location){
        if(serviceKind == null) ThrowHelper.illegalNull("serviceKind");
        if(comments == null) ThrowHelper.illegalNull("comments");
        if(location == null) ThrowHelper.illegalNull("location");

        this.serviceKind_ = serviceKind;
        this.comments_ = comments;
        this.employeeCreatedID_ = emp;
        this.serviceID_ = serviceID;
        this.location_ = location;
    }

    /**
     * Gets the serviceKind of the ServiceRequest
     * @return type of request
     */
    public ServiceKind serviceKind() {
        return serviceKind_;
    }

    /**
     * Sets the serviceKind of the ServiceRequest
     * @param serviceKind
     */
    public void setServiceKind(ServiceKind serviceKind) {
        this.serviceKind_ = serviceKind;
    }

    /**
     * Gets the comments of the ServiceRequest
     * @return comments on the request
     */
    public String comments() {
        return comments_;
    }

    /**
     * Sets the comments
     * @param comments
     */
    public void setComments(String comments) {
        this.comments_ = comments;
    }

    /**
     * Gets the location
     * @return
     */
    public String location() {
        return location_;
    }
}
