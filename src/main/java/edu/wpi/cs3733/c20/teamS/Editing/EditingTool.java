package edu.wpi.cs3733.c20.teamS.Editing;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

public abstract class EditingTool {
    public abstract void onNodeClicked(NodeData node);
    public abstract void onEdgeClicked(EndpointPair<NodeData> edge);
    public abstract void onMapClicked(double x, double y);

    public final void onEdgeClicked(NodeData start, NodeData end) {
        onEdgeClicked(EndpointPair.unordered(start, end));
    }
}
