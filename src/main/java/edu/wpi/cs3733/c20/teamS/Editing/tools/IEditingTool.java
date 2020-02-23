package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.input.MouseEvent;

public interface IEditingTool {
    default void onMapClicked(MouseEvent event) {}
    default void onMouseMovedOverMap(double x, double y) {}
    default void onNodeClicked(NodeData node, MouseEvent event) {}
    default void onEdgeClicked(EndpointPair<NodeData> edge) {}
    default void onEscapeKey() {}

    default void onNodeDragged(NodeData node, MouseEvent e) {}
    default void onNodeReleased(NodeData node, MouseEvent e) {}
}
