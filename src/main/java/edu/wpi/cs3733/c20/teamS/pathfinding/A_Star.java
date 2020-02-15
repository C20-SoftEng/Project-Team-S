package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.GraphNode;
import edu.wpi.cs3733.c20.teamS.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;


public class A_Star {
    private MutableGraph<NodeData> graph;
    private NodeData start;
    private NodeData goal;

    public A_Star (MutableGraph<T> graph, NodeData start, NodeData goal){
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");

        this.graph = graph;

        this.start = start;
        this.goal = goal;
    }


}
