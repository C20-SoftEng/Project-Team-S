package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.NodeData2;

public class PathingContext {

    IPathfinding algorithm;
    public PathingContext(IPathfinding alg){
        this.algorithm = alg;
    }

    public Iterable<NodeData2> executePathfind(MutableGraph<NodeData2> graph, NodeData2 start, NodeData2 goal){
        return algorithm.findPath(graph, start, goal);
    }
}
