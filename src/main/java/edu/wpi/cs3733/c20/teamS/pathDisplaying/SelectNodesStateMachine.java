package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Hitbox;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinder;
import edu.wpi.cs3733.c20.teamS.pathfinding.Path;
import edu.wpi.cs3733.c20.teamS.utilities.Numerics;
import edu.wpi.cs3733.c20.teamS.utilities.ReactiveProperty;
import edu.wpi.cs3733.c20.teamS.utilities.Vector2;
import io.reactivex.rxjava3.core.Observable;
import javafx.scene.input.MouseEvent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

import java.util.function.IntSupplier;
import java.util.stream.Collectors;

/**
 * This class handles the logic of finding a path when the user has clicked on a start node and
 * an end node. Simply call onNodeClicked() from your UI.
 */
final class SelectNodesStateMachine {
    private final MutableGraph<NodeData> graph;
    private final IPathfinder pathfinder;
    private final IntSupplier floorSupplier;
    private final ReactiveProperty<Path> path;
    private final ReactiveProperty<Optional<NodeData>> start;
    private final ReactiveProperty<Optional<NodeData>> goal;
    private State state;

    public SelectNodesStateMachine(MutableGraph<NodeData> graph, IPathfinder pathfinder, IntSupplier floorSupplier) {
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
    public void onNodeSelected(NodeData node) {
        state.onNodeSelected(node);
    }

    public void onHitboxClicked(Hitbox hitbox, MouseEvent event) {
        state.onHitboxClicked(hitbox, event);
//        event.consume();
//        Optional<NodeData> nearest = findNearestRealNode(hitbox, event.getX(), event.getY());
//        if (!nearest.isPresent())
//            return;
//
//        NodeData temp = createFakeNode(event.getX(), event.getY());
//        graph.addNode(temp);
//        tempNodes.add(temp);
//        graph.putEdge(temp, nearest.get());
//
//        onNodeSelected(temp);
    }

    private NodeData createFakeNode(double x, double y) {
        return new NodeData("FAKE", x, y,
                    floorSupplier.getAsInt(), "NONE",
                "FAKE", "FAKE NODE",
                "FAKE NODE");
    }

    public Path path() {
        return path.value();
    }
    public Observable<Path> pathChanged() {
        return path.changed();
    }
    public Optional<NodeData> start() {
        return start.value();
    }
    public Optional<NodeData> goal() {
        return goal.value();
    }

    private final class PinDrop {
        public final Hitbox hitbox;
        public final Vector2 position;

        public PinDrop(Hitbox hitbox, Vector2 position) {
            this.hitbox = hitbox;
            this.position = position;
        }
        public PinDrop(Hitbox hitbox, double x, double y) {
            this(hitbox, new Vector2(x, y));
        }
    }
    private abstract class State {

        public void onNodeSelected(NodeData node) {}
        public void onHitboxClicked(Hitbox hitbox, MouseEvent event) {}

        public final SelectNodesStateMachine outer() {
            return SelectNodesStateMachine.this;
        }
    }
    private final class NoSelectionState extends State {
        @Override
        public void onHitboxClicked(Hitbox hitbox, MouseEvent event) {
            NodeData fakeStart = createFakeNode(event.getX(), event.getY());
            outer().start.setValue(Optional.of(fakeStart));
            outer().goal.setValue(Optional.empty());
            outer().path.setValue(Path.empty());
            outer().state = new StartSelectedState(new PinDrop(hitbox, event.getX(), event.getY()));
        }
    }
    private final class StartSelectedState extends State {
        private final PinDrop start;

        public StartSelectedState(PinDrop start) {
            this.start = start;
        }

        @Override
        public void onHitboxClicked(Hitbox hitbox, MouseEvent event) {
            PinDrop end = new PinDrop(hitbox, event.getX(), event.getY());
            NodeData fakeStart = outer().start().get();

            NodeData fakeGoal = createFakeNode(end.position.x(), end.position.y());
            outer().goal.setValue(Optional.of(fakeGoal));
            Set<NodeData> realStartNodes = realNodesInHitbox(start.hitbox);
            Set<NodeData> realEndNodes = realNodesInHitbox(end.hitbox);

            Collection<Path> results = findAllPossiblePaths(fakeStart, fakeGoal, realStartNodes, realEndNodes);

            Path bestPath = results.stream()
                    .min(Comparator.comparingDouble(p -> p.cost()))
                    .orElse(Path.empty());

            outer().path.setValue(bestPath);

            outer().state = new NoSelectionState();
        }

        private Collection<Path> findAllPossiblePaths(
                NodeData fakeStart, NodeData fakeEnd,
                Set<NodeData> realStartNodes,
                Set<NodeData> realEndNodes) {
            graph.addNode(fakeStart);
            graph.addNode(fakeEnd);
            List<Path> results = new ArrayList<>();
            for (NodeData realStart : realStartNodes) {
                for (NodeData realEnd : realEndNodes) {
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

        private Set<NodeData> realNodesInHitbox(Hitbox hitbox) {
            return graph.nodes().stream()
                    .filter(node -> hitbox.touchingNodes().contains(node.getNodeID()))
                    .collect(Collectors.toSet());
        }
    }
}
