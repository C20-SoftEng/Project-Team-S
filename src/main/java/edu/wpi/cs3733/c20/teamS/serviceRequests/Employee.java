package edu.wpi.cs3733.c20.teamS.serviceRequests;


import edu.wpi.cs3733.c20.teamS.ThrowHelper;

public final class Employee {
    private final String name_;
    private final int id_;
    private final AccessLevel accessLevel_;

    public Employee(int id, String name) {
        this(id, name, AccessLevel.EMPLOYEE);
    }
    public Employee(int id, String name, AccessLevel level) {
        if (name == null) ThrowHelper.illegalNull("name");

        this.name_ = name;
        this.id_ = id;
        this.accessLevel_ = level;
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

    public static AccessLevel toAccess(int al){
        switch(al){
            case 0:
                return AccessLevel.USER;

            case 1:
                return AccessLevel.EMPLOYEE;

            case 2:
                return AccessLevel.ADMIN;


        }
        return null;
    }

    @Override
    public String toString(){
        return this.name_;
    }
}
