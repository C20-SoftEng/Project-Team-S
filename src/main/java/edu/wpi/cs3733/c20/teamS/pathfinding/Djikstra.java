package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

public final class Djikstra implements IPathfinder {
    private final AStar astar = new AStar();

    @Override
    public Path findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        return astar.findPath(
                graph, start, goal,
                (x, y) -> 0.0
        );
    }
}
