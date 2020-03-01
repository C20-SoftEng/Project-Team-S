package edu.wpi.cs3733.c20.teamS.Editing.CONDEMNEDtools;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.input.MouseEvent;

@Deprecated
public interface IEditingTool {
    default void onMapClicked(MouseEvent event) {}
    default void onMouseMoved(MouseEvent event) {}
    default void onNodeClicked(NodeData node, MouseEvent event) {}
    default void onEdgeClicked(EndpointPair<NodeData> edge, MouseEvent event) {}
    default void onRoomClicked(Room room, MouseEvent event) {}

    default void onNodeDragged(NodeData node, MouseEvent e) {}
    default void onNodeReleased(NodeData node, MouseEvent e) {}
    default void onClosed() {}
}
