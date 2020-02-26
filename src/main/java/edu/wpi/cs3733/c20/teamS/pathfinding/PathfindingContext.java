package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import java.util.function.Function;

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
    public Path findPath(
            NodeData start, NodeData goal,
            Function<NodeData, Iterable<NodeData>> friendSelector) {
        return pathfinder.findPath(start, goal, friendSelector);
    }
}
