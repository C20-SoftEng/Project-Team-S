package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableValueGraph;
import edu.wpi.cs3733.c20.teamS.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.Set;

public class DepthFirst implements IPathfinding{

    @Override
    public Set<NodeData> findPath(MutableValueGraph<NodeData, Double> graph, NodeData start, NodeData goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");

        return null;
    }
}
