package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.database.NodeData;

public final class RemoveNodeTool implements IEditingTool {
    private final ObservableGraph graph;

    public RemoveNodeTool(ObservableGraph graph) {
        this.graph = graph;
    }

    @Override
    public void onNodeClicked(NodeData node) {
        graph.removeNode(node);
    }
}
