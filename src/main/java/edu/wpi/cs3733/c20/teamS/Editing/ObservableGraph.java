package edu.wpi.cs3733.c20.teamS.Editing;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.Collections;
import java.util.Set;

/**
 * An observable undirected mutable graph that allows self-loops.
 */
public final class ObservableGraph {
    private final MutableGraph<NodeData> graph;

    private final PublishSubject<NodeData> nodeAdded = PublishSubject.create();
    private final PublishSubject<NodeData> nodeRemoved = PublishSubject.create();
    private final PublishSubject<EndpointPair<NodeData>> edgeAdded = PublishSubject.create();
    private final PublishSubject<EndpointPair<NodeData>> edgeRemoved = PublishSubject.create();

    public ObservableGraph() {
        this.graph = GraphBuilder.undirected().allowsSelfLoops(true).build();
    }

    public boolean addNode(NodeData node) {
        if (!graph.addNode(node))
            return false;
        nodeAdded.onNext(node);
        return true;
    }
    public boolean removeNode(NodeData node) {
        if (!graph.nodes().contains(node))
            return false;
        Set<NodeData> friends = graph.adjacentNodes(node);
        graph.removeNode(node);
        nodeRemoved.onNext(node);
        for (NodeData friend : friends)
            edgeRemoved.onNext(EndpointPair.unordered(node, friend));
        return true;
    }
    public boolean putEdge(NodeData start, NodeData end) {
        assert start != null : "start node was null in putEdge()";
        assert end != null : "end node was null in putEdge()";

        if (!graph.putEdge(start, end))
            return false;
        edgeAdded.onNext(EndpointPair.unordered(start, end));
        return true;
    }
    public boolean removeEdge(NodeData start, NodeData end) {
        assert start != null : "start was null in removeEdge()";
        assert end != null : "end was null in removeEdge()";

        if (!graph.removeEdge(start, end))
            return false;
        if (graph.removeEdge(end, start)) {
            System.out.println("Opposite edge was removed while normal one wasn't.");
        }
        edgeRemoved.onNext(EndpointPair.unordered(start, end));
        return true;
    }

    public MutableGraph<NodeData> inner() {
        return graph;
    }

    public Set<NodeData> nodes() {
        return Collections.unmodifiableSet(graph.nodes());
    }
    public Set<EndpointPair<NodeData>> edges() {
        return Collections.unmodifiableSet(graph.edges());
    }

    public Observable<NodeData> nodeAdded() {
        return nodeAdded;
    }
    public Observable<NodeData> nodeRemoved() {
        return nodeRemoved;
    }
    public Observable<EndpointPair<NodeData>> edgeAdded() {
        return edgeAdded;
    }
    public Observable<EndpointPair<NodeData>> edgeRemoved() {
        return edgeRemoved;
    }
}
