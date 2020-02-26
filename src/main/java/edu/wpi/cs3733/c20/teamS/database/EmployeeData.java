package edu.wpi.cs3733.c20.teamS.database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeData {
    private SimpleIntegerProperty employeeID;

    public EmployeeData(int employeeID, String username, String password, int accessLevel, String firstName, String lastName, String phoneNumber) {
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.accessLevel = new SimpleIntegerProperty(accessLevel);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
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
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.accessLevel = new SimpleIntegerProperty(accessLevel);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public int getEmployeeID() {
        return employeeID.get();
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID.set(employeeID);
    }

    public String getUsername() {
        return this.username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getAccessLevel() {
        return accessLevel.get();
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel.set(accessLevel);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhoneNumber() { return this.phoneNumber.get(); }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber.set(phoneNumber); }

    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleIntegerProperty accessLevel;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty phoneNumber;
}
