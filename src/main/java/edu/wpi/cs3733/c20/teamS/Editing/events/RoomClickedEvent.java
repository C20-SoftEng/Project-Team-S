package edu.wpi.cs3733.c20.teamS.Editing.events;

import edu.wpi.cs3733.c20.teamS.Editing.viewModels.EditRoomVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.scene.input.MouseEvent;

public class RoomClickedEvent {
    private final EditRoomVm room;
    private final MouseEvent event;

    public RoomClickedEvent(EditRoomVm room, MouseEvent event) {
        if (room == null) ThrowHelper.illegalNull("room");
        if (event == null) ThrowHelper.illegalNull("event");

        this.room = room;
        this.event = event;
    }

    public EditRoomVm room() {
        return room;
    }
    public MouseEvent event() {
        return event;
    }
}
