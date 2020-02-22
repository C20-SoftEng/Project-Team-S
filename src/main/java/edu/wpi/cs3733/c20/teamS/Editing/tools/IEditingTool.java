package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import org.checkerframework.framework.qual.NoDefaultQualifierForUse;

public interface IEditingTool {
    default void onMapClicked(double x, double y) {}
    default void onNodeClicked(NodeData node) {}
    default void onEdgeClicked(EndpointPair<NodeData> edge) {}
}
