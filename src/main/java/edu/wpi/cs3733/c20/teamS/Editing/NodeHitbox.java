package edu.wpi.cs3733.c20.teamS.Editing;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.shape.Polygon;


public class NodeHitbox {
    private final NodeData node;
    private final Polygon mask;

    public NodeHitbox(NodeData node, Polygon mask) {
        if (node == null) ThrowHelper.illegalNull("node");
        if (mask == null) ThrowHelper.illegalNull("hitbox");

        this.node = node;
        this.mask = mask;
    }

    public NodeData node() {
        return node;
    }
    public Polygon mask() {
        return mask;
    }
}
