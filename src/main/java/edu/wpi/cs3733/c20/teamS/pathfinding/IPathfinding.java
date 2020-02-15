package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.NodeData;

public interface IPathfinding {

    public Iterable<NodeData> findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal);
}
