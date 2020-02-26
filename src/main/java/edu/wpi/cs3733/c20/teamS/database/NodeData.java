package edu.wpi.cs3733.c20.teamS.database;


import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.geometry.Point2D;

import java.security.SecureRandom;
import java.util.Objects;

public class NodeData {
    private final PublishSubject<Point2D> positionChanged = PublishSubject.create();
    private static final SecureRandom rng = new SecureRandom();

    private String nodeID;
    private double xCoordinate;
    private double yCoordinate;
    private int floor;
    private String building;
    private String nodeType;
    private String longName;
    private String shortName;
    private double cost_ = 0;

    public NodeData(
            String nodeID, double xCoordinate, double yCoordinate,
            int floor, String building, String nodeType,
            String longName, String shortName) {
        this.nodeID = nodeID;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
    }

    public NodeData() {
        this.nodeID = nextUniqueID();
    }


    //Getters and setters.

    public String getNodeID() {
        return nodeID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData = (NodeData) o;
        return Double.compare(nodeData.xCoordinate, xCoordinate) == 0 &&
                Double.compare(nodeData.yCoordinate, yCoordinate) == 0 &&
                floor == nodeData.floor &&
                Objects.equals(nodeID, nodeData.nodeID) &&
                Objects.equals(building, nodeData.building) &&
                Objects.equals(nodeType, nodeData.nodeType) &&
                Objects.equals(longName, nodeData.longName) &&
                Objects.equals(shortName, nodeData.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeID, xCoordinate, yCoordinate, floor, building, nodeType, longName, shortName);
    }

    @Override
    public String toString() {
        return "NodeData{" +
                "nodeID='" + nodeID + '\'' +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", floor=" + floor +
                ", building='" + building + '\'' +
                ", nodeType='" + nodeType + '\'' +
                ", longName='" + longName + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }

    public double getxCoordinate() {
        return xCoordinate;
    }
    public double getyCoordinate() {
        return yCoordinate;
    }

    public int getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
    }
    public String getNodeType() {
        return nodeType;
    }
    public String getLongName() {
        return longName;
    }
    public String getShortName() {
        return shortName;
    }
    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }
    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
        positionChanged.onNext(new Point2D(this.xCoordinate, this.yCoordinate));
    }
    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
        positionChanged.onNext(new Point2D(this.xCoordinate, this.yCoordinate));
    }
    public void setPosition(double x, double y) {
        xCoordinate = x;
        yCoordinate = y;
        positionChanged.onNext(new Point2D(x, y));
    }
    public Observable<Point2D> positionChanged() {
        return positionChanged;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
    public void setBuilding(String building) {
        this.building = building;
    }
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
    public void setLongName(String longName) {
        this.longName = longName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public double cost() {return this.cost_; }

    public void setCost(double cost) {this.cost_ = cost; }

    public double distanceTo(NodeData other) {
        if (other == null) ThrowHelper.illegalNull("other");

        double xOffset = getxCoordinate() - other.getxCoordinate();
        double yOffset = getyCoordinate() - other.getyCoordinate();

        return Math.sqrt(xOffset * xOffset + yOffset * yOffset);
    }

    private static String nextUniqueID() {
        byte[] random = new byte[30];
        rng.nextBytes(random);
        StringBuilder result = new StringBuilder();
        for (byte n : random) {
            result.append(n % 10);
        }

        return result.toString();
    }
}
