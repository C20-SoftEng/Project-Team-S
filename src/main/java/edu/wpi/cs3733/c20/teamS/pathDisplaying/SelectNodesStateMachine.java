package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinder;
import edu.wpi.cs3733.c20.teamS.pathfinding.Path;
import edu.wpi.cs3733.c20.teamS.utilities.ReactiveProperty;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.Objects;
import java.util.Optional;

/**
 * This class handles the logic of finding a path when the user has clicked on a start node and
 * an end node. Simply call onNodeClicked() from your UI.
 */
final class SelectNodesStateMachine {
    private final MutableGraph<NodeData> graph;
    private final IPathfinder pathfinder;
    private final ReactiveProperty<Path> path;
    private final ReactiveProperty<Optional<NodeData>> start;
    private final ReactiveProperty<Optional<NodeData>> goal;
    private State state;

    public SelectNodesStateMachine(MutableGraph<NodeData> graph, IPathfinder pathfinder) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (pathfinder == null) ThrowHelper.illegalNull("pathfinder");

        this.graph = graph;
        this.pathfinder = pathfinder;
        path = new ReactiveProperty<>(Path.empty());
        start = new ReactiveProperty<>(Optional.empty());
        goal = new ReactiveProperty<>(Optional.empty());
        state = new NoSelectionState();
    }
    public void onNodeSelected(NodeData node) {
        state.onNodeSelected(node);
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
            outer().state = new NoSelectionState();
        }
    }
//    private final ReactiveProperty<Path> path = new ReactiveProperty<>(Path.empty());
//    private final MutableGraph<NodeData> graph;
//    private final IPathfinder pathfinder;
//
//    private State state;
//
//    public SelectNodesStateMachine(MutableGraph<NodeData> graph, IPathfinder pathfinder) {
//        if (graph == null) ThrowHelper.illegalNull("graph");
//        if (pathfinder == null) ThrowHelper.illegalNull("pathfinder");
//
//        this.graph = graph;
//        this.pathfinder = pathfinder;
//        state = new NothingChosenState();
//    }
//
//    /**
//     * Gets the currently-computed path. Will never be null; if no path has been computed it will be empty.
//     */
//    public Path path() {
//        return path.value();
//    }
//    public Observable<Path> pathChanged() {
//        return path.changed();
//    }
//    public void onNodeClicked(NodeData node) {
//        state.onNodeClicked(node);
//    }
//
//    private abstract static class State {
//        public abstract void onNodeClicked(NodeData node);
//    }
//
//    /**
//     * The user has not chosen a node yet, OR both nodes have been chosen
//     * and a previously-drawn path is being displayed.
//     */
//    private final class NothingChosenState extends State {
//        @Override
//        public void onNodeClicked(NodeData node) {
//            state = new StartNodeSelectedState(node);
//        }
//    }
//
//    /**
//     * The user has clicked on the start node, but not the end node, and no path is being displayed.
//     */
//    private final class StartNodeSelectedState extends State {
//        private final NodeData start;
//
//        public StartNodeSelectedState(NodeData start) {
//            this.start = start;
//            path.setValue(Path.empty());
//        }
//
//        @Override
//        public void onNodeClicked(NodeData node) {
//            path.setValue(pathfinder.findPath(graph, start, node));
//            state = new NothingChosenState();
//        }
//    }
}
