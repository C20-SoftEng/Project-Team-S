package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.Editing.ObservableGraph;
import edu.wpi.cs3733.c20.teamS.Editing.PartitionedParent;
import edu.wpi.cs3733.c20.teamS.Editing.events.EdgeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.events.RoomClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.EdgeVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.NodeVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.RoomVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.FloorSelector;
import edu.wpi.cs3733.c20.teamS.pathDisplaying.MapZoomer;
import edu.wpi.cs3733.c20.teamS.utilities.rx.RxAdaptors;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class EditableMap implements IEditableMap {
    private final ObservableGraph graph;
    private final ScrollPane scrollPane;
    private final ImageView mapImage;
    private final MapZoomer zoomer;
    private final FloorSelector floorSelector;
    private final Map<NodeData, NodeVm> nodeLookup = new HashMap<>();
    private final Map<EndpointPair<NodeData>, EdgeVm> edgeLookup = new HashMap<>();
    private final Map<Room, RoomVm> roomLookup = new HashMap<>();
    private final Group rootGroup;
    private final PartitionedParent<Integer, NodeVm> nodePartition = new PartitionedParent<>();
    private final PartitionedParent<Integer, EdgeVm> edgePartition = new PartitionedParent<>();
    private final PartitionedParent<Integer, RoomVm> roomPartition = new PartitionedParent<>();
    private final PartitionedParent<Integer, Node> auxiliaryPartition = new PartitionedParent<>();

    private final PublishSubject<NodeClickedEvent> nodeClicked = PublishSubject.create();
    private final PublishSubject<NodeClickedEvent> nodeDragged = PublishSubject.create();
    private final PublishSubject<NodeClickedEvent> nodeReleased = PublishSubject.create();
    private final PublishSubject<EdgeClickedEvent> edgeClicked = PublishSubject.create();
    private final PublishSubject<RoomClickedEvent> roomClicked = PublishSubject.create();
    private final Observable<MouseEvent> mapClicked;
    private final Observable<MouseEvent> mouseMoved;

    public EditableMap(
            MutableGraph<NodeData> graph,
            FloorSelector floorSelector, Collection<Room> rooms,
            ScrollPane scrollPane, ImageView mapImage
    ) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (floorSelector == null) ThrowHelper.illegalNull("floorSelector");
        if (rooms == null) ThrowHelper.illegalNull("rooms");

        this.floorSelector = floorSelector;
        this.scrollPane = scrollPane;
        zoomer = new MapZoomer(this.scrollPane);
        this.rootGroup = new Group();
        this.mapImage = mapImage;
        this.graph = createGraph();
        graph.nodes().forEach(this.graph::addNode);
        graph.edges().forEach(edge -> this.graph.putEdge(edge.nodeU(), edge.nodeV()));
        rooms.forEach(this::onRoomAdded);

        mapClicked = RxAdaptors.eventStream(this.rootGroup::setOnMouseClicked);
        mouseMoved = RxAdaptors.eventStream(this.rootGroup::setOnMouseMoved);

        floorSelector.currentChanged()
                .subscribe(n -> {
                   mapImage.setImage(floorSelector.floor(n).image);
                   nodePartition.setCurrentPartition(n);
                   edgePartition.setCurrentPartition(n);
                   roomPartition.setCurrentPartition(n);
                   auxiliaryPartition.setCurrentPartition(n);
                   updateZoom();
                });
        floorSelector.setCurrent(1);

        updateZoom();
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

    /**
     * Adds the specified UI widget to the root Parent object of the EditableMap. The added node will
     * be automatically hidden when the selected floor changes.
     * @param node The ui node to add.
     */
    public void addWidget(Node node) {
        auxiliaryPartition.putChild(selectedFloor(), node);
    }

    /**
     * Removes the specified UI widget from the root parent object of the EditableMap.
     * @param node The ui node to remove.
     */
    public void removeWidget(Node node) {
        auxiliaryPartition.removeChild(node);
    }

    public boolean addNode(NodeData node) {
        return graph.addNode(node);
    }
    public boolean removeNode(NodeData node) {
        return graph.removeNode(node);
    }
    public boolean putEdge(NodeData nodeU, NodeData nodeV) {
        return graph.putEdge(nodeU, nodeV);
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
    public int selectedFloor() {
        return floorSelector.current();
    }
    public boolean isPannable() {
        return scrollPane.isPannable();
    }
    public void setPannable(boolean value) {
        scrollPane.setPannable(value);
    }
    public Set<Room> rooms() {
        return roomLookup.keySet();
    }

    /**
     * Gets the view model used to render the specified node.
     * @param node The node to get the view-model for.
     * @return The view model that is rendering the specified node.
     */
    @Override
    public NodeVm getNodeViewModel(NodeData node) {
        if (node == null) ThrowHelper.illegalNull("node");

        return nodeLookup.get(node);
    }

    /**
     * Gets the view model for the specified edge.
     * @param edge The edge to get the view model for.
     * @return The view model that is used to render the edge.
     */
    @Override
    public EdgeVm getEdgeViewModel(EndpointPair<NodeData> edge) {
        if (edge == null) ThrowHelper.illegalNull("edge");

        return edgeLookup.get(edge);
    }
    /**
     * Gets the view model for the specified room.
     * @param room The room to get the view model for.
     * @return The view model that is used to render the specified room.
     */
    @Override
    public RoomVm getRoomViewModel(Room room) {
        if (room == null) ThrowHelper.illegalNull("room");

        return roomLookup.get(room);
    }

    public Observable<NodeClickedEvent> nodeClicked() {
        return nodeClicked;
    }
    public Observable<NodeClickedEvent> nodeDragged() {
        return nodeDragged;
    }
    public Observable<NodeClickedEvent> nodeReleased() {
        return nodeReleased;
    }
    public Observable<EdgeClickedEvent> edgeClicked() {
        return edgeClicked;
    }
    public Observable<RoomClickedEvent> roomClicked() {
        return roomClicked;
    }
    public Observable<MouseEvent> mapClicked() {
        return mapClicked;
    }
    public Observable<MouseEvent> mouseMoved() {
        return mouseMoved;
    }

    private void updateZoom() {
        double hval = scrollPane.getHvalue();
        double vval = scrollPane.getVvalue();

        rootGroup.getChildren().clear();
        rootGroup.getChildren().add(mapImage);
        rootGroup.getChildren().add(roomPartition);
        rootGroup.getChildren().add(edgePartition);
        rootGroup.getChildren().add(nodePartition);
        rootGroup.getChildren().add(auxiliaryPartition);

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
        result.setOnMouseClicked(e -> nodeClicked.onNext(new NodeClickedEvent(result, e)));
        result.setOnMouseDragged(e -> nodeDragged.onNext(new NodeClickedEvent(result, e)));
        result.setOnMouseReleased(e -> nodeReleased.onNext(new NodeClickedEvent(result, e)));

        return result;
    }
    private EdgeVm createEdgeVm(NodeData start, NodeData end) {
        assert start != null : "'start' can't be null.";
        assert end != null : "'end' can't be null.";

        EdgeVm result = new EdgeVm(start, end);
        result.setOnMouseClicked(e -> edgeClicked.onNext(new EdgeClickedEvent(result, e)));

        return result;
    }
    private RoomVm createRoomVm(Room room) {
        assert room != null : "'room' can't be null.";
        
        RoomVm result = new RoomVm(room);
        result.setOnMouseClicked(e -> roomClicked.onNext(new RoomClickedEvent(result, e)));
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
