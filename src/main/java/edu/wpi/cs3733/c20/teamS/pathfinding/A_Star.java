package edu.wpi.cs3733.c20.teamS.pathfinding;


import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.*;


public class A_Star implements IPathfinding {
    /**
     * Uses A* to find the path in the graph from the start node to the goal node
     *
     * Returns an empty array list if:
     *  start is not in the graph
     *  goal is not in the graph
     *  the graph is empty
     *  a path cannot be found
     *
     * @param graph the graph to traverse
     * @param start a node on the graph (start)
     * @param goal a node on the graph (destination)
     */
    @Override
    public Path findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        if(graph == null) ThrowHelper.illegalNull("graph");
        if(start == null) ThrowHelper.illegalNull("start");
        if(goal == null) ThrowHelper.illegalNull("goal");

        Comparator<Path> pathComparer = Comparator.comparingDouble(path -> {
            return path.cost() + distance(path.peek(), goal);
        });
        PriorityQueue<Path> queue = new PriorityQueue<>(pathComparer);
        HashSet<NodeData> seen = new HashSet<>();
        Path empty = Path.empty();
        queue.add(empty.push(start, 0));

        while (!queue.isEmpty()) {
            Path frontier = queue.poll();

            if (!seen.add(frontier.peek()))
                continue;
            if (frontier.peek().equals(goal))
                return frontier;

            for (NodeData friend : graph.adjacentNodes(frontier.peek())) {
                double knownCost = distance(friend, frontier.peek());
                queue.add(frontier.push(friend, knownCost));
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
    public static double distance(NodeData goal, NodeData current){
        if(goal == null) ThrowHelper.illegalNull("goal");
        if(current == null) ThrowHelper.illegalNull("current");

         return Math.sqrt((goal.getxCoordinate()-current.getxCoordinate())*(goal.getxCoordinate()-current.getxCoordinate()) +
                 (goal.getyCoordinate()-current.getyCoordinate())*(goal.getyCoordinate()-current.getyCoordinate()));
    }
}
