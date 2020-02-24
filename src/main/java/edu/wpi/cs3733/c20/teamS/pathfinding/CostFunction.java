package edu.wpi.cs3733.c20.teamS.pathfinding;

import edu.wpi.cs3733.c20.teamS.database.NodeData;

/**
 * A function that computes the cost of traversing between two nodes. This function must
 * be admissible; it must never overestimate the cost.
 */
@FunctionalInterface
public interface CostFunction {
    double cost(NodeData start, NodeData goal);
}
