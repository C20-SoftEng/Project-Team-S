package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinding;

public class PathRenderer {
    private final PathFinderStateMachine pathFinder;

    public PathRenderer(MutableGraph<NodeData> graph, IPathfinding algorithm) {
        pathFinder = new PathFinderStateMachine(graph, algorithm);
    }

    public void redraw() {

    }

    public void onNodeClicked(NodeData node) {

    }
}
