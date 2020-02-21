package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.*;

public class BreadthFirst implements IPathfinding{

    @Override
    public Path findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (start == null) ThrowHelper.illegalNull("start");
        if (goal == null) ThrowHelper.illegalNull("goal");

        LinkedList<Path> queue = new LinkedList<>();
        HashSet<NodeData> seen = new HashSet<>();
        Path empty = Path.empty();
        queue.addFirst(empty.push(start, 0));
        seen.add(start);

        while (!queue.isEmpty()) {
            Path frontier = queue.pollLast();
            if (frontier.peek().equals(goal))
                return frontier;

            for (NodeData friend : graph.adjacentNodes(frontier.peek())) {
                if (!seen.add(friend))
                    continue;
                queue.addFirst(frontier.push(friend, 0));
            }
        }
        return empty;
    }
}
