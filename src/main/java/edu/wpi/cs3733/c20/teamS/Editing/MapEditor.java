package edu.wpi.cs3733.c20.teamS.Editing;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableGraph;
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapEditor {
    private final ObservableGraph graph;
    private IEditingTool editingTool;
    private final ScrollPane scrollPane;
    private final ImageView mapImage;
    private final MapZoomer zoomer;
    private final Map<NodeData, NodeVm> nodeLookup = new HashMap<>();
    private final Map<EndpointPair<NodeData>, EdgeVm> edgeLookup = new HashMap<>();
    private final Map<Room, RoomVm> roomLookup = new HashMap<>();
    private final Group rootGroup;
    private final PartitionedParent<Integer, NodeVm> nodePartition = new PartitionedParent<>();
    private final PartitionedParent<Integer, EdgeVm> edgePartition = new PartitionedParent<>();
    private final PartitionedParent<Integer, RoomVm> roomPartition = new PartitionedParent<>();

    public MapEditor(
            MutableGraph<NodeData> graph,
            FloorSelector floorSelector, Collection<Room> rooms,
            ScrollPane scrollPane, ImageView mapImage
    ) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (floorSelector == null) ThrowHelper.illegalNull("floorSelector");
        if (rooms == null) ThrowHelper.illegalNull("rooms");

        this.editingTool = new IEditingTool() {};
        this.scrollPane = scrollPane;
        zoomer = new MapZoomer(this.scrollPane);
        this.rootGroup = new Group();
        this.mapImage = mapImage;

        this.graph = createGraph();
        graph.nodes().forEach(this.graph::addNode);
        graph.edges().forEach(edge -> this.graph.putEdge(edge.nodeU(), edge.nodeV()));
        rooms.forEach(this::onRoomAdded);

        rootGroup.setOnMouseClicked(e -> editingTool.onMapClicked(e));
        rootGroup.setOnMouseMoved(e -> editingTool.onMouseMoved(e));
        floorSelector.currentChanged()
                .subscribe(n -> {
                   mapImage.setImage(floorSelector.floor(n).image);
                   nodePartition.setCurrentPartition(n);
                   edgePartition.setCurrentPartition(n);
                   roomPartition.setCurrentPartition(n);
                   updateZoom();
                });

        updateZoom();
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
    public ObservableGraph graph() {
        return graph;
    }

    public boolean canZoomIn() {
        return zoomer.canZoomIn();
    }
    public boolean canZoomOut() {
        return zoomer.canZoomOut();
    }
    public void zoomIn() {
        zoomer.zoomIn();
        updateZoom();
    }
    public void zoomOut() {
        zoomer.zoomOut();
        updateZoom();
    }

    public boolean addNode(NodeData node) {
        return graph.addNode(node);
    }
    public boolean removeNode(NodeData node) {
        return graph.removeNode(node);
    }
    public boolean putEdge(EndpointPair<NodeData> edge) {
        return graph.putEdge(edge.nodeU(), edge.nodeV());
    }
    public boolean putEdge(NodeData nodeU, NodeData nodeV) {
        return graph.putEdge(nodeU, nodeV);
    }
    public boolean removeEdge(EndpointPair<NodeData> edge) {
        return graph.removeEdge(edge.nodeU(), edge.nodeV());
    }
    public boolean removeEdge(NodeData nodeU, NodeData nodeV) {
        return graph.removeEdge(nodeU, nodeV);
    }
    public boolean addRoom(Room room) {
        if (roomLookup.containsKey(room))
            return false;
        onRoomAdded(room);
        return true;
    }
    public boolean removeRoom(Room room) {
        if (!roomLookup.containsKey(room))
            return false;
        onRoomRemoved(room);
        return true;
    }

    private void updateZoom() {
        double hval = scrollPane.getHvalue();
        double vval = scrollPane.getVvalue();

        rootGroup.getChildren().clear();
        rootGroup.getChildren().add(mapImage);
        rootGroup.getChildren().add(roomPartition);
        rootGroup.getChildren().add(edgePartition);
        rootGroup.getChildren().add(nodePartition);

        scrollPane.setContent(rootGroup);

        zoomer.zoomSet();
        scrollPane.setHvalue(hval);
        scrollPane.setVvalue(vval);
    }
    private ObservableGraph createGraph() {
        ObservableGraph graph = new ObservableGraph();

        graph.nodeAdded().subscribe(this::onNodeAdded);
        graph.nodeRemoved().subscribe(this::onNodeRemoved);
        graph.edgeAdded().subscribe(this::onEdgeAdded);
        graph.edgeRemoved().subscribe(this::onEdgeRemoved);

        return graph;
    }
    private NodeVm createNodeVm(NodeData node) {
        assert node != null : "node can't be null!";

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
    private void onNodeAdded(NodeData node) {
        NodeVm vm = createNodeVm(node);
        nodeLookup.put(node, vm);
        nodePartition.putChild(node.getFloor(), vm);
    }
    private void onNodeRemoved(NodeData node) {
        NodeVm remove = nodeLookup.get(node);
        nodePartition.removeChild(remove);
        nodeLookup.remove(node);
    }
    private void onEdgeAdded(EndpointPair<NodeData> edge) {
        EdgeVm vm = createEdgeVm(edge.nodeU(), edge.nodeV());
        edgePartition.putChild(edge.nodeU().getFloor(), vm);
        edgeLookup.put(edge, vm);
    }
    private void onEdgeRemoved(EndpointPair<NodeData> edge) {
        EdgeVm remove = edgeLookup.get(edge);
        edgePartition.removeChild(remove);
        edgeLookup.remove(edge);
    }
    private void onRoomAdded(Room room) {
        RoomVm vm = createRoomVm(room);
        roomPartition.putChild(room.floor(), vm);
        roomLookup.put(room, vm);
    }
    private void onRoomRemoved(Room room) {
        RoomVm remove = roomLookup.get(room);
        roomPartition.removeChild(remove);
        roomLookup.remove(room);
    }
}
