package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinder;
import edu.wpi.cs3733.c20.teamS.pathfinding.Path;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReactiveProperty;
import io.reactivex.rxjava3.core.Observable;

import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

/**
 * This class handles the logic of finding a path when the user has clicked on a start node and
 * an end node. Simply call onNodeClicked() from your UI.
 */
final class NodeSelector {
    private final MutableGraph<NodeData> graph;
    private final IPathfinder pathfinder;
    private final IntSupplier floorSupplier;
    private final ReactiveProperty<Path> path;
    private final ReactiveProperty<Optional<PinDrop>> start;
    private final ReactiveProperty<Optional<PinDrop>> goal;
    private State state;

    public NodeSelector(MutableGraph<NodeData> graph, IPathfinder pathfinder, IntSupplier floorSupplier) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (pathfinder == null) ThrowHelper.illegalNull("current");
        if (floorSupplier == null) ThrowHelper.illegalNull("floorSupplier");

        this.graph = graph;
        this.pathfinder = pathfinder;
        this.floorSupplier = floorSupplier;
        path = new ReactiveProperty<>(Path.empty());
        start = new ReactiveProperty<>(Optional.empty());
        goal = new ReactiveProperty<>(Optional.empty());
        state = new NoSelectionState();
    }

    /**
     * Call this whenever the user clicks on, or otherwise selects, a room. The value of start, goal,
     * and path will be updated appropriately depending on which nodes have been selected so far.
     * @param room The room they selected.
     * @param x The x-coordinate of the point in the room that was clicked.
     * @param y The y-coordinate of the point in the room that was clicked.
     */
    public void onRoomClicked(Room room, double x, double y) {
        if (room == null) ThrowHelper.illegalNull("room");

        state.onRoomClicked(room, x, y);
    }

    /**
     * Call this whenever the user clicks on, or otherwise selects, a node.
     * @param node The node that the user selected.
     */
    public void onNodeClicked(NodeData node) {
        if (node == null) ThrowHelper.illegalNull("node");

        state.onNodeClicked(node);
    }
    public void reset() {
        start.setValue(Optional.empty());
        goal.setValue(Optional.empty());
        path.setValue(Path.empty());
    }
    private NodeData createFakeNode(double x, double y) {
        return new NodeData("FAKE", x, y,
                    floorSupplier.getAsInt(), "NONE",
                "FAKE", "FAKE NODE",
                "FAKE NODE");
    }
    private boolean isNodeFake(NodeData node) {
        return node.getNodeID().equals("FAKE");
    }

    public Path path() {
        return path.value();
    }
    public Observable<Path> pathChanged() {
        return path.changed();
    }
    public Optional<PinDrop> start() {
        return start.value();
    }
    public Observable<Optional<PinDrop>> startChanged() {
        return start.changed();
    }
    public Optional<PinDrop> goal() {
        return goal.value();
    }
    public Observable<Optional<PinDrop>> goalChanged() {
        return goal.changed();
    }

    private abstract class State {

        public void onRoomClicked(Room room, double x, double y) {}
        public void onNodeClicked(NodeData node) {}
        public final NodeSelector outer() {
            return NodeSelector.this;
        }
    }
    private final class NoSelectionState extends State {
        @Override public void onRoomClicked(Room room, double x, double y) {
            NodeData fakeStart = createFakeNode(x, y);
            onEndpointClicked(fakeStart, room);
        }
        @Override public void onNodeClicked(NodeData node) {
            Room room = createFakeRoomFromRealNode(node);
            room.touchingNodes().add(node.getNodeID());
            onEndpointClicked(node, room);
        }

        private void onEndpointClicked(NodeData node, Room room) {
            outer().start.setValue(Optional.of(new PinDrop(room, node)));
            outer().goal.setValue(Optional.empty());
            outer().path.setValue(Path.empty());
            outer().state = new StartSelectedState(start.value().get());
        }
    }

    private Room createFakeRoomFromRealNode(NodeData node) {
        Room room = new Room();
        room.setName(node.getLongName());
        return room;
    }

    private final class StartSelectedState extends State {
        private final PinDrop start;

        public StartSelectedState(PinDrop start) {
            this.start = start;
        }

        @Override public void onRoomClicked(Room room, double x, double y) {
            if (room == null) ThrowHelper.illegalNull("room");

            PinDrop goal = new PinDrop(room, createFakeNode(x, y));

            doMostOfPathfindingShit(goal);
        }

        private void doMostOfPathfindingShit(PinDrop goal) {
            Set<NodeData> realStartNodes = realNodesInHitbox(start.room());
            Set<NodeData> realEndNodes = realNodesInHitbox(goal.room());

            Collection<Path> results = findAllPossiblePaths(
                    start.node(), goal.node(),
                    realStartNodes, realEndNodes
            );

            Path bestPath = results.stream()
                    .min(Comparator.comparingDouble(Path::cost))
                    .orElse(Path.empty());

            outer().goal.setValue(Optional.of(goal));
            outer().path.setValue(bestPath);

            outer().state = new NoSelectionState();
        }

        @Override public void onNodeClicked(NodeData node) {
            if (node == null) ThrowHelper.illegalNull("node");

            Room room = createFakeRoomFromRealNode(node);
            room.touchingNodes().add(node.getNodeID());
            PinDrop goal = new PinDrop(room, node);
            doMostOfPathfindingShit(goal);
            //onRoomClicked(room, node.getxCoordinate(), node.getyCoordinate());
        }

        private Collection<Path> findAllPossiblePaths(
                NodeData fakeStart, NodeData fakeEnd,
                Set<NodeData> realStartNodes,
                Set<NodeData> realEndNodes) {
            graph.addNode(fakeStart);
            graph.addNode(fakeEnd);
            List<Path> results = new ArrayList<>();
            for (NodeData realStart : realStartNodes) {
                if (realStart == fakeStart)
                    continue;
                for (NodeData realEnd : realEndNodes) {
                    if (realEnd == fakeEnd)
                        continue;
                    graph.putEdge(fakeStart, realStart);
                    graph.putEdge(fakeEnd, realEnd);
                    Path path = pathfinder.findPath(graph, fakeStart, fakeEnd);
                    results.add(path);
                    graph.removeEdge(fakeStart, realStart);
                    graph.removeEdge(fakeEnd, realEnd);
                }
            }
            graph.removeNode(fakeStart);
            graph.removeNode(fakeEnd);
            return results;
        }

        private Set<NodeData> realNodesInHitbox(Room room) {
            return graph.nodes().stream()
                    .filter(node -> room.touchingNodes().contains(node.getNodeID()))
                    .collect(Collectors.toSet());
        }
    }
}
