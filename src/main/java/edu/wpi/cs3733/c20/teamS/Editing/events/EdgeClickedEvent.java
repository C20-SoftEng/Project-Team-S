package edu.wpi.cs3733.c20.teamS.Editing.events;

import edu.wpi.cs3733.c20.teamS.Editing.viewModels.EdgeVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.scene.input.MouseEvent;

public class EdgeClickedEvent {
    private final EdgeVm edge;
    private final MouseEvent event;

    public EdgeClickedEvent(EdgeVm edge, MouseEvent event) {
        if (edge == null) ThrowHelper.illegalNull("edge");
        if (event == null) ThrowHelper.illegalNull("event");

        this.edge = edge;
        this.event = event;
    }

    public EdgeVm edge() {
        return edge;
    }
    public MouseEvent event() {
        return event;
    }
}
