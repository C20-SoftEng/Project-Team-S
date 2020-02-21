package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.scene.Node;

import java.util.*;

public class DepthFirst implements IPathfinding {
    @Override
    public Path findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");

        Stack<Path> stack = new Stack<>();
        Set<NodeData> seen = new HashSet<>();
        Path empty = Path.empty();
        stack.push(empty.push(start, 0));
        seen.add(start);

        while (!stack.isEmpty()) {
            Path frontier = stack.pop();
            if (frontier.peek().equals(goal))
                return frontier;
            for (NodeData friend : graph.adjacentNodes(frontier.peek())) {
                if (!seen.add(friend))
                    continue;
                stack.push(frontier.push(friend, 0));
            }
        }
        return empty;
    }

    /**
     * A heuristic function that uses the euclidean distance
     * @param goal the goal node
     * @param current the current node
     * @return the euclidean distance
     */
    public double euclideanDistance(NodeData goal, NodeData current){
        if(goal == null) ThrowHelper.illegalNull("goal");
        if(current == null) ThrowHelper.illegalNull("current");

        return Math.sqrt((goal.getxCoordinate()-current.getxCoordinate())*(goal.getxCoordinate()-current.getxCoordinate()) + (goal.getyCoordinate()-current.getyCoordinate())*(goal.getyCoordinate()-current.getyCoordinate()));
    }




}
