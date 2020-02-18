package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;

import java.util.ArrayList;


public interface IPathfinding {

    public ArrayList<GraphNode> findPath(MutableGraph<GraphNode> graph, GraphNode start, GraphNode goal);
}
