package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableGraph;

import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.function.Supplier;

/**
 * A UI-agnostic object that is responsible for making edits to a graph based on user input.
 * To connect GraphEditor to your UI, simply call the methods on selectedTool in your UI event handlers.
 */
public class GraphEditor {
    private final Supplier<Integer> floorNumberSupplier;
    private EditingTool selectedTool;

    final DatabaseController database = new DatabaseController();
    final MutableGraph<NodeData> graph;
    final PublishSubject<NodeData> nodeAdded = PublishSubject.create();
    final PublishSubject<NodeData> nodeRemoved = PublishSubject.create();
    final PublishSubject<EndpointPair<NodeData>> edgeAdded = PublishSubject.create();
    final PublishSubject<EndpointPair<NodeData>> edgeRemoved = PublishSubject.create();

    public GraphEditor(MutableGraph<NodeData> graph, Supplier<Integer> floorNumberSupplier) {
        this.graph = graph;
        this.floorNumberSupplier = floorNumberSupplier;
        selectedTool = new AddNodeTool(this);
    }

    public EditingTool selectedTool() {
        return selectedTool;
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

    public void selectAddNodeTool() {
        selectedTool = new AddNodeTool(this);
    }
    public void selectRemoveNodeTool() {
        selectedTool = new RemoveNodeTool(this);
    }
    public void selectAddEdgeTool() {
        selectedTool = new AddEdgeTool(this);
    }
    public void selectRemoveEdgeTool() {
        selectedTool = new RemoveEdgeTool(this);
    }

    int floorNumber() {
        return floorNumberSupplier.get();
    }


}
