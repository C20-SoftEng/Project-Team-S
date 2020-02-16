package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.*;

public class BreadthFirst implements IPathfinding{

    @Override
    public ArrayList<NodeData> findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");


        return null;
    }
}
