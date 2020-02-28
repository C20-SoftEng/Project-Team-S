package edu.wpi.cs3733.c20.teamS.Editing;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.Editing.tools.IEditingTool;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.EdgeVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.NodeVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.RoomVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

public class MapEditor {
    private IEditingTool editingTool;

    public MapEditor(IEditingTool initialEditingTool) {
        if (initialEditingTool == null) ThrowHelper.illegalNull("initialEditingTool");

        this.editingTool = initialEditingTool;
    }

    public NodeVm createNodeVm(NodeData node) {
        NodeVm result = new NodeVm(node);
        result.setOnMouseClicked(e -> editingTool.onNodeClicked(node, e));
        result.setOnMouseDragged(e -> editingTool.onNodeDragged(node, e));
        result.setOnMouseReleased(e -> editingTool.onNodeReleased(node, e));

        return result;
    }
    public EdgeVm createEdgeVm(NodeData start, NodeData end) {
        EdgeVm edgeVm = new EdgeVm(start, end);
        edgeVm.setOnMouseClicked(e -> {
            EndpointPair<NodeData> edge = EndpointPair.unordered(start, end);
            editingTool.onEdgeClicked(edge, e);
        });

        return edgeVm;
    }
    public RoomVm createRoomVm(Room room) {
        RoomVm result = new RoomVm(room);
        result.setOnMouseClicked(e -> editingTool.onRoomClicked(room, e));
        return result;
    }
    public IEditingTool editingTool() {
        return editingTool;
    }
    public void setEditingTool(IEditingTool value) {
        IEditingTool previous = this.editingTool;
        this.editingTool = value;
        if (previous == null)
            return;
        previous.onClosed();
    }
}
