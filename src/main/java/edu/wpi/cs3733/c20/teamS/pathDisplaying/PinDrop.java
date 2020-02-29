package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

public final class PinDrop {
    private final Room room;
    private final NodeData node;

    PinDrop(Room room, NodeData node) {
        this.room = room;
        this.node = node;
    }

    public Room room() {
        return room;
    }
    public NodeData node() {
        return node;
    }
}
