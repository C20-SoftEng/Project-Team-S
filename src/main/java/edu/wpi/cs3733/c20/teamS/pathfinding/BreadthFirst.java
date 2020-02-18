package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class BreadthFirst implements IPathfinding{

    @Override
    public ArrayList<GraphNode> findPath(MutableGraph<GraphNode> graph, GraphNode start, GraphNode goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");

        throw new NotImplementedException();
    }
}
