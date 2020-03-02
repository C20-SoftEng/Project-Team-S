package edu.wpi.cs3733.c20.teamS.Editing.events;

import edu.wpi.cs3733.c20.teamS.Editing.viewModels.RoomVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.scene.input.MouseEvent;

public class RoomClickedEvent {
    private final RoomVm room;
    private final MouseEvent event;

    public RoomClickedEvent(RoomVm room, MouseEvent event) {
        if (room == null) ThrowHelper.illegalNull("room");
        if (event == null) ThrowHelper.illegalNull("event");

        this.room = room;
        this.event = event;
    }

    public RoomVm room() {
        return room;
    }
    public MouseEvent event() {
        return event;
    }
}
