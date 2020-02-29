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
import edu.wpi.cs3733.c20.teamS.pathDisplaying.MapZoomer;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

import java.util.Set;
import java.util.stream.Collectors;

public class MapEditor {
    private final ObservableGraph graph;
    private final FloorSelector floorSelector;
    private final Set<Room> rooms;
    private IEditingTool editingTool;
    private final ScrollPane scrollPane;
    private final Group group;
    private final ImageView mapImage;
    private final MapZoomer zoomer;

    public MapEditor(
            ObservableGraph graph, IEditingTool initialEditingTool,
            FloorSelector floorSelector, Set<Room> rooms,
            ScrollPane scrollPane, Group group, ImageView mapImage
    ) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (floorSelector == null) ThrowHelper.illegalNull("floorSelector");
        if (rooms == null) ThrowHelper.illegalNull("rooms");
        if (initialEditingTool == null) ThrowHelper.illegalNull("initialEditingTool");

        this.graph = graph;
        this.floorSelector = floorSelector;
        this.rooms = rooms;
        this.editingTool = initialEditingTool;
        this.scrollPane = scrollPane;
        this.group = group;
        this.mapImage = mapImage;
        zoomer = new MapZoomer(this.scrollPane);


    }

    private Group drawAllNodes() {
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
    private Group drawAllEdges() {
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
    private Group drawAllRooms() {
        Group result = new Group();
        rooms.stream()
                .filter(room -> room.floor() == floorSelector.current())
                .map(this::createRoomVm)
                .forEach(vm -> result.getChildren().add(vm));
        return result;
    }

    private NodeVm createNodeVm(NodeData node) {
        NodeVm result = new NodeVm(node);
        result.setOnMouseClicked(e -> editingTool.onNodeClicked(node, e));
        result.setOnMouseDragged(e -> editingTool.onNodeDragged(node, e));
        result.setOnMouseReleased(e -> editingTool.onNodeReleased(node, e));

        return result;
    }
    private EdgeVm createEdgeVm(NodeData start, NodeData end) {
        EdgeVm edgeVm = new EdgeVm(start, end);
        edgeVm.setOnMouseClicked(e -> {
            EndpointPair<NodeData> edge = EndpointPair.unordered(start, end);
            editingTool.onEdgeClicked(edge, e);
        });

        return edgeVm;
    }
    private RoomVm createRoomVm(Room room) {
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

    public void redrawMap() {
        double currentHval = scrollPane.getHvalue();
        double currentVval = scrollPane.getVvalue();

        group.getChildren().clear();
        group.getChildren().add(mapImage);

        group.getChildren().add(drawAllRooms());
        group.getChildren().add(drawAllEdges());
        group.getChildren().add(drawAllNodes());

        scrollPane.setContent(group);

        //Keeps the zoom the same throughout each screen/floor change.
        zoomer.zoomSet();
        scrollPane.setHvalue(currentHval);
        scrollPane.setVvalue(currentVval);
    }
    public boolean canZoomIn() {
        return zoomer.canZoomIn();
    }
    public boolean canZoomOut() {
        return zoomer.canZoomOut();
    }
    public void zoomIn() {
        zoomer.zoomIn();
    }
    public void zoomOut() {
        zoomer.zoomOut();
    }
}
