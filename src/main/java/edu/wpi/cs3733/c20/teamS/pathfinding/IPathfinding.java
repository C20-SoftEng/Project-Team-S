package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.NodeData2;

import java.util.ArrayList;


public interface IPathfinding {

    public ArrayList<NodeData2> findPath(MutableGraph<NodeData2> graph, NodeData2 start, NodeData2 goal);
}
