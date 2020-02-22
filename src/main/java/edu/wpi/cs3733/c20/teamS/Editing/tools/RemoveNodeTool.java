package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

final class RemoveNodeTool extends EditingTool {

    private final GraphEditor graphEditor;

    public RemoveNodeTool(GraphEditor graphEditor) {
        this.graphEditor = graphEditor;
    }

    @Override
    public void onNodeClicked(NodeData node) {
        graphEditor.graph.removeNode(node);
        graphEditor.database.removeNode(node.getNodeID());
        graphEditor.nodeRemoved.onNext(node);
    }
    @Override
    public void onEdgeClicked(EndpointPair<NodeData> edge) {}
    @Override
    public void onMapClicked(double x, double y) {}
}
