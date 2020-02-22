package edu.wpi.cs3733.c20.teamS.database;

public class EdgeData{
    //attributes
    private String edgeID;
    private String startNode;
    private String endNode;

    public EdgeData(NodeData start, NodeData end) {
        this(start.getNodeID() + end.getNodeID(), start.getNodeID(), end.getNodeID());
    }
    public EdgeData(String edgeID, String startNode, String endNode){
        this.edgeID = edgeID;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public String getEdgeID(){
        return this.edgeID;
    }
    public void setEdgeID(String edgeID){
        this.edgeID = edgeID;
    }

    public String getStartNode(){
        return this.startNode;
    }
    public void setStartNode(String startNode){
        this.startNode = startNode;
    }

    public String getEndNode(){
        return this.endNode;
    }
    public void setEndNode(String endNode){
        this.endNode = endNode;
    }

    @Override
    public String toString() {
        return "EdgeData{" +
                "edgeID='" + edgeID + '\'' +
                ", startNode='" + startNode + '\'' +
                ", endNode='" + endNode + '\'' +
                '}';
    }
}
