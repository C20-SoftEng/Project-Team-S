package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;

public class PathingContext {

    IPathfinding algorithm;
    public PathingContext(IPathfinding alg){
        this.algorithm = alg;
    }

    public Iterable<GraphNode> executePathfind(MutableGraph<GraphNode> graph, GraphNode start, GraphNode goal){
        return algorithm.findPath(graph, start, goal);
    }
}
