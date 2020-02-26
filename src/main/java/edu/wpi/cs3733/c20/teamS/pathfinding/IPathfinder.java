package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import java.util.ArrayList;
import java.util.function.Function;


public interface IPathfinder {

    public Path findPath(NodeData start, NodeData goal, Function<NodeData, Iterable<NodeData>> friendSelector);

    default public Path findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (start == null) ThrowHelper.illegalNull("start");
        if (goal == null) ThrowHelper.illegalNull("goal");

        return findPath(start, goal, node -> graph.adjacentNodes(node));
    }
}
