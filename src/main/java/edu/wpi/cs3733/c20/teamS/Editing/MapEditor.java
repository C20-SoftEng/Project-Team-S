package edu.wpi.cs3733.c20.teamS.Editing;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableGraph;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class MapEditor {
    private final MutableGraph<NodeData> graph;
    private final Supplier<Integer> floorNumberSupplier;
    private final PublishSubject<NodeData> nodeAdded = PublishSubject.create();
    private final PublishSubject<NodeData> nodeRemoved = PublishSubject.create();
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

    private class AddNodeTool extends EditingTool {

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

    private static String padDigits(int value, int totalDigits) {
        String result = Integer.toString(value);
        while (result.length() < totalDigits)
            result = '0' + result;

        return result;
    }
}
