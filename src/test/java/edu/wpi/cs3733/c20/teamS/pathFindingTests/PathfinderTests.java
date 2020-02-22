package edu.wpi.cs3733.c20.teamS.pathFindingTests;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.AStar;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinding;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class PathfinderTests {
    protected final NodeData node(double x, double y, int floor) {
        return new NodeData(
                "test", x, y, floor, "No Building",
                "Test Node", "Test Node", "TN");
    }
    protected final NodeData node(double x, double y) {
        return node(x, y, 0);
    }
}
