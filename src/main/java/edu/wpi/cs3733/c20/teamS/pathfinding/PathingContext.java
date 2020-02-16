package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableValueGraph;
import edu.wpi.cs3733.c20.teamS.NodeData;

public class PathingContext {

    IPathfinding algorithm;
    public PathingContext(IPathfinding alg){
        this.algorithm = alg;
    }

    public Iterable<NodeData> executePathfind(MutableValueGraph<NodeData, Double> graph, NodeData start, NodeData goal){
        return algorithm.findPath(graph, start, goal);
    }
}
