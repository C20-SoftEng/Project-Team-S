package edu.wpi.cs3733.c20.teamS.pathfinding;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import java.util.function.Function;

/**
 * An implementation of IPathfinder that allows the algorithm to be switched on the fly.
 */
public final class PathfindingContext implements IPathfinder {
    private IPathfinder pathfinder;

    public PathfindingContext(IPathfinder pathfinder) {
        setCurrent(pathfinder);
    }

    /**
     * Sets the algorithm used for pathfinding.
     * @param pathfinder The algorithm to use for pathfinding.
     */
    public void setCurrent(IPathfinder pathfinder) {
        if (pathfinder == null) ThrowHelper.illegalNull("current");
        if (pathfinder == this)
            throw new IllegalArgumentException(
                    "Setting current IPathfinder to 'this' is " +
                    "a recipe for unbounded recursion.");

        this.pathfinder = pathfinder;
    }

    /**
     * Gets the currently-selected pathfinding algorithm.
     */
    public IPathfinder current() {
        return pathfinder;
    }

    @Override
    public Path findPath(
            NodeData start, NodeData goal,
            Function<NodeData, Iterable<NodeData>> friendSelector) {
        return pathfinder.findPath(start, goal, friendSelector);
    }
}
