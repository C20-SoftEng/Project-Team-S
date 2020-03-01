package edu.wpi.cs3733.c20.teamS.Editing.events;

import edu.wpi.cs3733.c20.teamS.Editing.viewModels.NodeVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

/**
 * Event data for when a node is clicked by the mouse.
 */
public class NodeClickedEvent {
    private final NodeVm node;
    private final MouseEvent event;
    private Vector2 parentPosition = null;

    public NodeClickedEvent(NodeVm node, MouseEvent event) {
        if (node == null) ThrowHelper.illegalNull("node");
        if (event == null) ThrowHelper.illegalNull("event");

        this.node = node;
        this.event = event;
    }

    public NodeVm node() {
        return node;
    }
    public MouseEvent event() {
        return event;
    }

    /**
     * Gets the mouse position in parent coordinates.
     * @return The mouse position in parent coordinates.
     */
    public Vector2 parentPosition() {
        if (parentPosition == null) {
            Point2D point = node.localToParent(event.getX(), event.getY());
            parentPosition = new Vector2(point.getX(), point.getY());
        }

        return parentPosition;
    }
}
