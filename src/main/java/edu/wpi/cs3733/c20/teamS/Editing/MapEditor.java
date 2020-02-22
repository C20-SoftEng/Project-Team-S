package edu.wpi.cs3733.c20.teamS.Editing;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableGraph;

import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EdgeData;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Supplier;

public class MapEditor {
    private final MutableGraph<NodeData> graph;
    private final Supplier<Integer> floorNumberSupplier;
    private final PublishSubject<NodeData> nodeAdded = PublishSubject.create();
    private final PublishSubject<NodeData> nodeRemoved = PublishSubject.create();
    private final PublishSubject<EndpointPair<NodeData>> edgeAdded = PublishSubject.create();
    private final PublishSubject<EndpointPair<NodeData>> edgeRemoved = PublishSubject.create();
    private final DatabaseController database = new DatabaseController();

    public MapEditor(MutableGraph<NodeData> graph, Supplier<Integer> floorNumberSupplier) {
        this.graph = graph;
        this.floorNumberSupplier = floorNumberSupplier;
        selectedTool = new AddNodeTool();
    }

    private EditingTool selectedTool;

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
        selectedTool = new AddNodeTool();
    }
    public void selectRemoveNodeTool() {
        selectedTool = new RemoveNodeTool();
    }
    public void selectAddEdgeTool() {
        selectedTool = new AddEdgeTool();
    }
    public void selectRemoveEdgeTool() {
        selectedTool = new RemoveEdgeTool();
    }

    private final class AddNodeTool extends EditingTool {
        @Override
        public void onNodeClicked(NodeData node) {}
        @Override
        public void onEdgeClicked(EndpointPair<NodeData> edge) {}
        @Override
        public void onMapClicked(double x, double y) {
            Stage stage = new Stage();

            NodeEditScreen.showDialog(stage)
                    .subscribe(e -> {
                        if (e.result() == DialogResult.OK) {
                            e.value().setNodeID(generateUniqueID(e.value()));
                            e.value().setBuilding("Faulkner");
                            e.value().setxCoordinate(x);
                            e.value().setyCoordinate(y);
                            e.value().setFloor(floorNumberSupplier.get());

                            database.addNode(e.value());
                            graph.addNode(e.value());

                            nodeAdded.onNext(e.value());
                        }
                        stage.close();
                    });
        }

        private String generateUniqueID(NodeData node) {
            Optional<Integer> max = graph.nodes().stream()
                    .filter(n -> n.getFloor() == floorNumberSupplier.get())
                    .filter(n -> n.getNodeType().equals(node.getNodeType()))
                    .map(n -> n.getNodeID())
                    .map(id -> id.substring(5, 8))
                    .map(id -> Integer.parseInt(id))
                    .sorted()
                    .max((x, y) -> Integer.compare(x, y));
            int num = max.isPresent() ? max.get() + 1 : 1;

            return "S" + node.
                    getNodeType().toUpperCase() +
                    padDigits(num, 3) +
                    padDigits(node.getFloor(), 2);
        }
        private String padDigits(int value, int totalDigits) {
            String result = Integer.toString(value);
            while (result.length() < totalDigits)
                result = '0' + result;

            return result;
        }
    }

    private final class RemoveNodeTool extends EditingTool {

        @Override
        public void onNodeClicked(NodeData node) {
            graph.removeNode(node);
            database.removeNode(node.getNodeID());
            nodeRemoved.onNext(node);
        }
        @Override
        public void onEdgeClicked(EndpointPair<NodeData> edge) {}
        @Override
        public void onMapClicked(double x, double y) {}
    }

    private final class AddEdgeTool extends EditingTool {
        private State state;

        public AddEdgeTool() {
            this.state = new StandbyState();
        }

        @Override
        public void onNodeClicked(NodeData node) {
            state.onNodeClicked(node);
        }
        @Override
        public void onEdgeClicked(EndpointPair<NodeData> edge) {}
        @Override
        public void onMapClicked(double x, double y) {}

        private abstract class State {
            public abstract void onNodeClicked(NodeData node);
        }
        private final class StandbyState extends State {
            @Override
            public void onNodeClicked(NodeData node) {
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
                if (!graph.putEdge(start, node))
                    return;

                EdgeData ed = new EdgeData(start, node);
                database.addEdge(ed);
                edgeAdded.onNext(EndpointPair.unordered(start, node));

                AddEdgeTool.this.state = new StandbyState();
            }
        }
    }

    private final class RemoveEdgeTool extends EditingTool {

        @Override
        public void onNodeClicked(NodeData node) {}
        @Override
        public void onMapClicked(double x, double y) {}

        @Override
        public void onEdgeClicked(EndpointPair<NodeData> edge) {
            if (!graph.removeEdge(edge))
                return;
            EdgeData ed = new EdgeData(edge.nodeU(), edge.nodeV());
            database.removeEdge(ed.getEdgeID());
            edgeRemoved.onNext(edge);
        }
    }
}
