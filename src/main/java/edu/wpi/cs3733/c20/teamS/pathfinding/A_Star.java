package edu.wpi.cs3733.c20.teamS.pathfinding;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

public class A_Star {
    private Graph graph;
    private GraphNode start;
    private GraphNode goal;

    public A_Star (Graph graph, GraphNode start, GraphNode goal){
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");

        this.graph = graph;
        this.start = start;
        this.goal = goal;
    }

    
}
