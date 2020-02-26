package edu.wpi.cs3733.c20.teamS.pathfinding;


import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import org.checkerframework.framework.qual.NoDefaultQualifierForUse;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;


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
     * @param start The node we start at.
     * @param goal The node we are trying to reach.
     * @param friendSelector A function that gives us the nodes that are adjacent to a node.
     * @param costFunction A function to calculate the cost of traversing between two nodes.
     */
    public Path findPath(
            NodeData start,
            NodeData goal,
            Function<NodeData, Iterable<NodeData>> friendSelector,
            CostFunction costFunction) {

        if (start == null) ThrowHelper.illegalNull("start");
        if (goal == null) ThrowHelper.illegalNull("goal");
        if (friendSelector == null) ThrowHelper.illegalNull("friendSelector");
        if (costFunction == null) ThrowHelper.illegalNull("costFunction");

        Comparator<Path> pathComparer = Comparator.comparingDouble(
                path -> path.cost() + costFunction.cost(path.peek(), goal)
        );
        HashSet<NodeData> seen = new HashSet<>();
        PriorityQueue<Path> queue = new PriorityQueue<>(pathComparer);
        queue.add(Path.empty().push(start, 0));

        while (!queue.isEmpty()) {
            Path frontier = queue.poll();

            if (!seen.add(frontier.peek()))
                continue;
            if (frontier.peek().equals(goal))
                return frontier;

            for (NodeData friend : friendSelector.apply(frontier.peek())) {
                double knownCost = costFunction.cost(frontier.peek(), friend);
                queue.add(frontier.push(friend, knownCost));
            }
        }
        return Path.empty();
    }

    @Override
    public Path findPath(
            NodeData start, NodeData goal,
            Function<NodeData, Iterable<NodeData>> friendSelector) {
        return findPath(start, goal, friendSelector, defaultCostFunction());
    }
    public Path findPath(
            MutableGraph<NodeData> graph,
            NodeData start, NodeData goal,
            CostFunction costFunction
    ) {
        return findPath(start, goal, node -> graph.adjacentNodes(node), costFunction);
    }

    private static CostFunction defaultCostFunction() {
        return (start, goal) -> start.getFloor() == goal.getFloor() ?
                distance(start, goal) :
                ELEVATOR_COST;
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
