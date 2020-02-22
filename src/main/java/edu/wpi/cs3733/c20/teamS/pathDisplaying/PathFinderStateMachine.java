package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.pathfinding.IPathfinding;
import edu.wpi.cs3733.c20.teamS.pathfinding.Path;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Objects;

/**
 * This class handles the logic of finding a path when the user has clicked on a start node and
 * an end node.
 */
final class PathFinderStateMachine {
    private final PublishSubject<Path> pathChanged = PublishSubject.create();
    private final MutableGraph<NodeData> graph;
    private final IPathfinding pathfinder;

    private Path path = Path.empty();
    private State state = new NoNodesSelectedState();

    public PathFinderStateMachine(MutableGraph<NodeData> graph, IPathfinding pathfinder) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (pathfinder == null) ThrowHelper.illegalNull("pathfinder");

        this.graph = graph;
        this.pathfinder = pathfinder;
    }

    /**
     * Gets the currently-computed path. Will never be null; if no path has been computed it will be empty.
     */
    public Path path() {
        return path;
    }
    public Observable<Path> pathChanged() {
        return pathChanged;
    }
    public void onNodeClicked(NodeData node) {
        state.onNodeClicked(node);
    }

    private void setPath(Path path) {
        if (Objects.equals(this.path, path))
            return;

        this.path = path;
        pathChanged.onNext(path);
    }

    private abstract static class State {
        public abstract void onNodeClicked(NodeData node);
    }
    private final class NoNodesSelectedState extends State {
        @Override
        public void onNodeClicked(NodeData node) {
            setPath(Path.empty());
            state = new StartNodeSelectedState(node);
        }
    }
    private final class StartNodeSelectedState extends State {
        private final NodeData start;

        public StartNodeSelectedState(NodeData start) {
            this.start = start;
        }

        @Override
        public void onNodeClicked(NodeData node) {
            Path path = pathfinder.findPath(graph, start, node);
            setPath(path);
            state = new NoNodesSelectedState();
        }
    }
}
