package edu.wpi.cs3733.c20.teamS.serviceRequests;

import edu.wpi.cs3733.c20.teamS.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

public abstract class ServiceRequest {
    private final Integer id_;
    private Employee assignee_;
    private ServiceStatus status_;
    private String message_;
    private NodeData location_;

    protected ServiceRequest(Integer id) {
        this.id_ = id;
        status_ = ServiceStatus.CREATED;
    }

    public abstract void accept(RequestVisitor visitor);
    public Integer id() {
        return id_;
    }
    public Employee assignee() {
        return assignee_;
    }
    public ServiceStatus status() {
        return status_;
    }
    public String message() {
        return message_;
    }
    public void setMessage(String value) {
        this.message_ = value;
    }
    public NodeData location() {
        return location_;
    }
    public void setLocation(NodeData value) {
        this.location_ = value;
    }
    public void assignTo(Employee employee) {
        if (employee == null) ThrowHelper.illegalNull("employee");

        assignee_ = employee;
        status_ = ServiceStatus.ASSIGNED;
    }
    public void complete() {
        status_ = ServiceStatus.COMPLETED;
    }
    public void cancel() {
        status_ = ServiceStatus.CANCELED;
        assignee_ = null;
    }
}
