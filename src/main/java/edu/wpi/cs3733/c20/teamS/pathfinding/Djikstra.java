package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import java.util.function.Function;

public final class Djikstra implements IPathfinder {
    private final AStar astar = new AStar();

    @Override
    public Path findPath(
            NodeData start, NodeData goal,
            Function<NodeData, Iterable<NodeData>> friendSelector
    ) {
        return astar.findPath(start, goal, friendSelector, (x, y) -> 0);
    }
}
