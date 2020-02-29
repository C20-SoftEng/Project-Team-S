package edu.wpi.cs3733.c20.teamS.Editing.events;

import edu.wpi.cs3733.c20.teamS.Editing.viewModels.NodeVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.scene.input.MouseEvent;

/**
 * Event data for when a node is clicked by the mouse.
 */
public class NodeClickedEvent {
    private final NodeVm node;
    private final MouseEvent event;

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
}
