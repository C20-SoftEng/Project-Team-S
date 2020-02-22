package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

final class RemoveEdgeTool extends EditingTool {

    private final GraphEditor graphEditor;

    public RemoveEdgeTool(GraphEditor graphEditor) {
        this.graphEditor = graphEditor;
    }

    @Override
    public void onNodeClicked(NodeData node) {}
    @Override
    public void onMapClicked(double x, double y) {}

    @Override
    public void onEdgeClicked(EndpointPair<NodeData> edge) {
        if (!graphEditor.graph.removeEdge(edge))
            return;
        EdgeData ed = new EdgeData(edge.nodeU(), edge.nodeV());
        graphEditor.database.removeEdge(ed.getEdgeID());
        graphEditor.edgeRemoved.onNext(edge);
    }
}
