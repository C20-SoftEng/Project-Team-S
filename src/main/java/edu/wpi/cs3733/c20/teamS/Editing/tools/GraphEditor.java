package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.checkerframework.framework.qual.NoDefaultQualifierForUse;

import java.util.Collections;
import java.util.Set;

public final class GraphEditor {
    private final MutableGraph<NodeData> graph;
    private final DatabaseController database;

    private final PublishSubject<NodeData> nodeAdded = PublishSubject.create();
    private final PublishSubject<NodeData> nodeRemoved = PublishSubject.create();
    private final PublishSubject<EndpointPair<NodeData>> edgeAdded = PublishSubject.create();
    private final PublishSubject<EndpointPair<NodeData>> edgeRemoved = PublishSubject.create();

    public GraphEditor(MutableGraph<NodeData> graph, DatabaseController database) {
        if (graph == null) ThrowHelper.illegalNull("graph");
        if (database == null) ThrowHelper.illegalNull("database");

        this.graph = graph;
        this.database = database;
    }

    public boolean addNode(NodeData node) {
        if (!graph.addNode(node))
            return false;
        database.addNode(node);
        nodeAdded.onNext(node);
        return true;
    }
    public boolean removeNode(NodeData node) {
        if (!graph.removeNode(node))
            return false;
        database.removeNode(node.getNodeID());
        nodeRemoved.onNext(node);
        return true;
    }
    public boolean putEdge(NodeData start, NodeData end) {
        if (!graph.putEdge(start, end))
            return false;
        database.addEdge(new EdgeData(start, end));
        edgeAdded.onNext(EndpointPair.unordered(start, end));
        return true;
    }
    public boolean removeEdge(NodeData start, NodeData end) {
        if (!graph.removeEdge(start, end))
            return false;
        database.removeEdge(new EdgeData(start, end).getEdgeID());
        edgeRemoved.onNext(EndpointPair.unordered(start, end));
        return true;
    }
    public Set<NodeData> nodes() {
        return Collections.unmodifiableSet(graph.nodes());
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
