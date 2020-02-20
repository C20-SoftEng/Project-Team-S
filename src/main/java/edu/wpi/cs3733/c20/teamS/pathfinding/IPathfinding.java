package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import java.util.ArrayList;


public interface IPathfinding {

    public ArrayList<NodeData> findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal);
}
