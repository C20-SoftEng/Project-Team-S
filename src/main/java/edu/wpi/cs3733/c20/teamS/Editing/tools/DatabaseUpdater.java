package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;

public final class DatabaseUpdater {
    private final ObservableGraph graph;
    private final DatabaseController database;

    public DatabaseUpdater(ObservableGraph graph) {
        if (graph == null) ThrowHelper.illegalNull("graph");

        this.graph = graph;
        database = new DatabaseController();

        this.graph.nodeAdded().subscribe(node -> database.addNode(node));
        this.graph.nodeRemoved().subscribe(node -> database.removeNode(node.getNodeID()));
        this.graph.edgeAdded().subscribe(
                edge -> database.addEdge(new EdgeData(edge.nodeU(), edge.nodeV())));
        this.graph.edgeRemoved().subscribe(
                edge -> database.removeEdge(new EdgeData(edge.nodeU(), edge.nodeV()).getEdgeID()));
    }
}
