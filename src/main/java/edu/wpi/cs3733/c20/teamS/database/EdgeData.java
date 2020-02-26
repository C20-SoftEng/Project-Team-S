package edu.wpi.cs3733.c20.teamS.database;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

public class EdgeData{
    private static final String NODE_ID_SEPARATOR = "_";
    private String edgeID;
    private String startNode;
    private String endNode;

    public EdgeData(NodeData start, NodeData end) {
        if (start == null) ThrowHelper.illegalNull("start");
        if (end == null) ThrowHelper.illegalNull("end");

        initEdgeIDUsingOrdinalConsistency(start.getNodeID(), end.getNodeID());

    }
    public EdgeData(String leftID, String rightID) {
        if (leftID == null) ThrowHelper.illegalNull("startNodeID");
        if (rightID == null) ThrowHelper.illegalNull("endNodeID");

        initEdgeIDUsingOrdinalConsistency(leftID, rightID);
    }

    private void initEdgeIDUsingOrdinalConsistency(String leftID, String rightID) {
        int compareResult = leftID.compareTo(rightID);
        String lower = compareResult < 0 ?
                leftID : rightID;
        String higher = compareResult >= 0 ?
                leftID : rightID;
        edgeID = lower + NODE_ID_SEPARATOR + higher;
        startNode = lower;
        endNode = higher;
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
