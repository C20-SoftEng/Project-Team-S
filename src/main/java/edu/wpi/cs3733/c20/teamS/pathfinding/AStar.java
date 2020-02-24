package edu.wpi.cs3733.c20.teamS.pathfinding;


import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.*;
import java.util.function.BiFunction;


public final class AStar implements IPathfinder {
    private static final double ELEVATOR_COST = 100000;
    /**
     * Uses A* to find the path in the graph from the start node to the goal node
     *
     * Returns an empty path if:
     *  start is not in the graph
     *  goal is not in the graph
     *  the graph is empty
     *  a path cannot be found
     *
     * @param graph the graph to traverse
     * @param start a node on the graph (start)
     * @param goal a node on the graph (destination)
     * @param costFunction
     */
    public Path findPath(
            MutableGraph<NodeData> graph,
            NodeData start, NodeData goal,
            CostFunction costFunction) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (start == null) ThrowHelper.illegalNull("start");
        if (goal == null) ThrowHelper.illegalNull("goal");
        if (costFunction == null) ThrowHelper.illegalNull("costFunction");

        Comparator<Path> pathComparer = Comparator.comparingDouble(
                path -> path.cost() + costFunction.cost(path.peek(), goal)
        );
        PriorityQueue<Path> queue = new PriorityQueue<>(pathComparer);
        HashSet<NodeData> seen = new HashSet<>();
        queue.add(Path.empty().push(start, 0));

        while (!queue.isEmpty()) {
            Path frontier = queue.poll();

            if (!seen.add(frontier.peek()))
                continue;
            if (frontier.peek().equals(goal))
                return frontier;

            for (NodeData friend : graph.adjacentNodes(frontier.peek())) {
                //double knownCost = distance(friend, frontier.peek());
                double knownCost = costFunction.cost(frontier.peek(), friend);
                queue.add(frontier.push(friend, knownCost));
            }
        }

        return Path.empty();
    }

    @Override
    public Path findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        CostFunction costFunction = (x, y) ->
                x.getFloor() == y.getFloor() ?
                        distance(x, y) :
                        ELEVATOR_COST;
        return findPath(graph, start, goal, costFunction);
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

        double cost = current.distanceTo(goal);
        if (current.getFloor() != goal.getFloor())
            cost += ELEVATOR_COST;

        return cost;
    }
}
