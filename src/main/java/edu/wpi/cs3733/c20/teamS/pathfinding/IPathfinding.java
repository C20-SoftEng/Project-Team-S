package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableValueGraph;
import edu.wpi.cs3733.c20.teamS.NodeData;

import java.util.Set;

public interface IPathfinding {

    public Set<NodeData> findPath(MutableValueGraph<NodeData, Double> graph, NodeData start, NodeData goal);
}
