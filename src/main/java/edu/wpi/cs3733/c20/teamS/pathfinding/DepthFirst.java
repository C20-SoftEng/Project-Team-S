package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.*;
import java.util.function.Function;

public class DepthFirst implements IPathfinder {
    @Override
    public Path findPath(
            NodeData start, NodeData goal,
            Function<NodeData, Iterable<NodeData>> friendSelector) {
        if (start == null) ThrowHelper.illegalNull("start");
        if (goal == null) ThrowHelper.illegalNull("goal");
        if (friendSelector == null) ThrowHelper.illegalNull("friendSelector");

        Stack<Path> stack = new Stack<>();
        Set<NodeData> seen = new HashSet<>();
        Path empty = Path.empty();
        stack.push(empty.push(start, 0));
        seen.add(start);

        while (!stack.isEmpty()) {
            Path frontier = stack.pop();
            if (frontier.peek().equals(goal))
                return frontier;
            for (NodeData friend : friendSelector.apply(frontier.peek())) {
                if (!seen.add(friend))
                    continue;
                stack.push(frontier.push(friend, 0));
            }
        }
        return empty;
    }


}
