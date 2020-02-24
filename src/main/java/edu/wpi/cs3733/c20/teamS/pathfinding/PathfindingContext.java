package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

/**
 * An implementation of IPathfinder that allows the algorithm to be switched on the fly.
 */
public class PathfindingContext implements IPathfinder {
    private IPathfinder pathfinder;

    public PathfindingContext(IPathfinder pathfinder) {
        if (pathfinder == null) ThrowHelper.illegalNull("pathfinder");

        this.pathfinder = pathfinder;
    }

    /**
     * Sets the algorithm used for pathfinding.
     * @param pathfinder The algorithm to use for pathfinding.
     */
    public void setPathfinder(IPathfinder pathfinder) {
        if (pathfinder == null) ThrowHelper.illegalNull("pathfinder");

        this.pathfinder = pathfinder;
    }
    public IPathfinder pathfinder() {
        return pathfinder;
    }

    @Override
    public Path findPath(MutableGraph<NodeData> graph, NodeData start, NodeData goal) {
        return pathfinder.findPath(graph, start, goal);
    }
}
