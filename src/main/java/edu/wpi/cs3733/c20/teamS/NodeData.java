package edu.wpi.cs3733.c20.teamS;

public class NodeData {
    private double x_;
    private double y_;
    private String nodeID;
    private int floor_;
    private String building;
    private String nodeType;
    private String longName;
    private String shortName;
    private double cost_ = 0;

//        public NodeData(double x, double y){
//
//            nodeID = Long.toString(System.currentTimeMillis() + this.hashCode());
//            this.x_ = x;
//            this.y_ = y;
//        }

    public NodeData(String nodeID, double x, double y,
                    int floor, String building, String nodeType, String longName, String shortName){
        this.nodeID = nodeID;
        this.x_ = x;
        this.y_ = y;
        this.floor_ = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
    }

    public double x() {
        return this.x_;
    }
    public double y() { return this.y_; }
    public String nodeID() { return this.nodeID; }
    public int floor() { return this.floor_; }
    public String building() { return this.building; }
    public String nodeType() { return this.nodeType; }
    public String longName() { return this.longName; }
    public String shortName() { return this.shortName; }
    public double cost() {return this.cost_; }


    public void setPosition(double x, double y) {
        double previousX = x_;
        double previousY = y_;

        x_ = x;
        y_ = y;
    }
    public void setNodeID(String nodeID){ this.nodeID = nodeID; }
    public void setFloor_(int floor_){ this.floor_ = floor_; }
    public void setBuilding(String building){ this.building = building; }
    public void setNodeType(String nodeType){ this.nodeType = nodeType; }
    public void setLongName(String longName){ this.longName = longName; }
    public void setShortName(String shortName){ this.shortName = shortName; }
    public void setCost(double cost) {this.cost_ = cost; }

    @Override
    public String toString(){
        return "Node ID" + nodeID;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }

        if(!(o instanceof NodeData)){
            return false;
        }

        NodeData nodeData = (NodeData)o;

        return this.nodeID == nodeData.nodeID;
    }

    @Override
    public int hashCode() {
        return this.nodeID.hashCode();
    }
}
