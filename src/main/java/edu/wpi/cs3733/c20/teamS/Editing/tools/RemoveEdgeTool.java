package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

public final class RemoveEdgeTool implements IEditingTool {
    private final ObservableGraph graph;

    public RemoveEdgeTool(ObservableGraph graph) {
        this.graph = graph;
    }

    @Override
    public void onEdgeClicked(EndpointPair<NodeData> edge) {
        graph.removeEdge(edge.nodeU(), edge.nodeV());
    }
}
