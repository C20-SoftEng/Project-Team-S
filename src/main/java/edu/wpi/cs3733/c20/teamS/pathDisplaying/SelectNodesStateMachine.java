package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Hitbox;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinder;
import edu.wpi.cs3733.c20.teamS.pathfinding.Path;
import edu.wpi.cs3733.c20.teamS.utilities.Numerics;
import edu.wpi.cs3733.c20.teamS.utilities.ReactiveProperty;
import io.reactivex.rxjava3.core.Observable;
import javafx.scene.input.MouseEvent;

import java.util.Comparator;
import java.util.HashSet;

import java.util.Optional;
import java.util.Set;
import java.util.function.IntSupplier;

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
    private final Set<NodeData> tempNodes = new HashSet<>();

    public SelectNodesStateMachine(MutableGraph<NodeData> graph, IPathfinder pathfinder, IntSupplier floorSupplier) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (pathfinder == null) ThrowHelper.illegalNull("pathfinder");
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
    private Optional<NodeData> findNearestRealNode(Hitbox hitbox, double x, double y) {
        return graph.nodes().stream()
                .filter(node -> hitbox.touchingNodes().contains(node.getNodeID()))
                .filter(node -> !tempNodes.contains(node))
                .min(Comparator.comparingDouble(node -> nodeDistance(node, x, y)));
    }

    private static double nodeDistance(NodeData node, double x, double y) {
        return Numerics.distance(x, y, node.getxCoordinate(), node.getyCoordinate());
    }
    private void removeTempNodes() {
        for (NodeData node : tempNodes)
            graph.removeNode(node);
        tempNodes.clear();
    }
    public void onHitboxClicked(Hitbox hitbox, MouseEvent event) {
        event.consume();
        Optional<NodeData> nearest = findNearestRealNode(hitbox, event.getX(), event.getY());
        if (!nearest.isPresent())
            return;

        NodeData temp = createFakeNode(event.getX(), event.getY());
        graph.addNode(temp);
        tempNodes.add(temp);
        graph.putEdge(temp, nearest.get());

        onNodeSelected(temp);
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

    private abstract class State {

        public void onNodeSelected(NodeData node) {}

        public final SelectNodesStateMachine outer() {
            return SelectNodesStateMachine.this;
        }
    }

    private final class NoSelectionState extends State {
        @Override public void onNodeSelected(NodeData node) {
            state = new StartSelectedState(node);
        }
    }
    private final class StartSelectedState extends State {
        public StartSelectedState(NodeData start) {
            outer().start.setValue(Optional.of(start));
            outer().goal.setValue(Optional.empty());
            outer().path.setValue(Path.empty());
        }

        @Override public void onNodeSelected(NodeData node) {
            outer().goal.setValue(Optional.of(node));
            Path path = pathfinder.findPath(graph, start.value().get(), goal.value().get());
            outer().path.setValue(path);
            removeTempNodes();
            outer().state = new NoSelectionState();
        }
    }
}
