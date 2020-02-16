package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BreadthFirst implements IPathfinding{

    @Override
    public Set<NodeData> findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");


        LinkedList<NodeData> queue = new LinkedList<NodeData>();
        LinkedList<NodeData> visited = new LinkedList<NodeData>();

        //first input (key) is the node after the second input
        //a key gets the node where the path came from
        HashMap<NodeData, NodeData> cameFrom = new HashMap<>();
        cameFrom.put(start, null);

        NodeData current = start;

        while(current != goal){
            Set<NodeData> neighbors = graph.adjacentNodes(current);
            for (NodeData neighbor: neighbors) {
                cameFrom.put(neighbor, current);
            }
        }

        return null;
    }
}
