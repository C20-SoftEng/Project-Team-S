package edu.wpi.cs3733.c20.teamS.serviceRequests;


import edu.wpi.cs3733.c20.teamS.ThrowHelper;

public final class Employee {
    private final String name_;
    private final int id_;
    private final AccessLevel accessLevel_;

    public Employee(int id, String name) {
        if (name == null) ThrowHelper.illegalNull("name");

        this.name_ = name;
        this.id_ = id;
        this.accessLevel_ = AccessLevel.EMPLOYEE;
    }

    public String name() {
        return name_;
    }

    public int id() {
        return id_;
    }

    public AccessLevel accessLevel() {
        return accessLevel_;
    }
}
