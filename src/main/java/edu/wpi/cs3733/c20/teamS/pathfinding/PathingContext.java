package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.NodeData2;
import edu.wpi.cs3733.c20.teamS.database.DataClasses.NodeData;

public class PathingContext {

    IPathfinding algorithm;
    public PathingContext(IPathfinding alg){
        this.algorithm = alg;
    }

    public Iterable<NodeData> executePathfind(MutableGraph<NodeData> graph, NodeData start, NodeData goal){
        return algorithm.findPath(graph, start, goal);
    }
}
