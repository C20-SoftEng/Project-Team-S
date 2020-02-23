package edu.wpi.cs3733.c20.teamS.database;

public class EmployeeData {
    private int employeeID;

    public EmployeeData(int employeeID, String username, String password, int accessLevel, String firstName, String lastName, String phoneNumber) {
        this.employeeID = employeeID;
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "EmployeeData{" +
                "employeeID=" + employeeID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accessLevel=" + accessLevel +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public EmployeeData(String username, String password, int accessLevel, String firstName, String lastName, String phoneNumber) {
        //this.employeeID = employeeID;
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String username;
    private String password;
    private int accessLevel;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
