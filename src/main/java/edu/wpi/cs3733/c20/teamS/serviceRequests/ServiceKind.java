package edu.wpi.cs3733.c20.teamS.serviceRequests;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

/**
 * A class that contains a String identifying what kind of service is requested
 */
public final class ServiceKind {
    private String name_;

    /**
     * Constructor for ServiceKind
     * @param name the name of the service
     */
    public ServiceKind(String name){
        if(name==null) ThrowHelper.illegalNull("name");
        this.name_ = name;
    }

    /**
     * Gets the name of the Service
     * @return
     */
    public String name() {
        return name_;
    }
}
