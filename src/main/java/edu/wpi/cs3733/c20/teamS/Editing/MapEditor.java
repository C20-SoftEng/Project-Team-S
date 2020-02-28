package edu.wpi.cs3733.c20.teamS.Editing;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.Editing.tools.IEditingTool;
import edu.wpi.cs3733.c20.teamS.Editing.tools.ObservableGraph;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.EdgeVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.NodeVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.RoomVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.FloorSelector;
import javafx.scene.Group;

import java.util.Set;
import java.util.stream.Collectors;

public class MapEditor {
    private final ObservableGraph graph;
    private final FloorSelector floorSelector;
    private final Set<Room> rooms;
    private IEditingTool editingTool;

    public MapEditor(
            ObservableGraph graph, IEditingTool initialEditingTool,
            FloorSelector floorSelector, Set<Room> rooms
    ) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (floorSelector == null) ThrowHelper.illegalNull("floorSelector");
        if (rooms == null) ThrowHelper.illegalNull("rooms");
        if (initialEditingTool == null) ThrowHelper.illegalNull("initialEditingTool");

        this.graph = graph;
        this.floorSelector = floorSelector;
        this.rooms = rooms;
        this.editingTool = initialEditingTool;
    }

    public Group drawAllNodes() {
        Group group = new Group();
        Set<NodeData> nodes = graph.nodes().stream()
                .filter(node -> node.getFloor() == floorSelector.current())
                .collect(Collectors.toSet());

        for (NodeData node : nodes) {
            NodeVm vm = createNodeVm(node);
            group.getChildren().add(vm);
        }
        return group;
    }
    public Group drawAllEdges() {
        Group group = new Group();
        graph.edges().stream()
                .filter(edge -> {
                    return edge.nodeU().getFloor() == floorSelector.current() ||
                            edge.nodeV().getFloor() == floorSelector.current();
                })
                .map(edge -> createEdgeVm(edge.nodeU(), edge.nodeV()))
                .forEach(vm -> group.getChildren().add(vm));

        return group;
    }
    public Group drawAllRooms() {
        Group result = new Group();
        rooms.stream()
                .filter(room -> room.floor() == floorSelector.current())
                .map(this::createRoomVm)
                .forEach(vm -> result.getChildren().add(vm));
        return result;
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
