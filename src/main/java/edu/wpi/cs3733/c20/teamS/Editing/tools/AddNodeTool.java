package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.NodeEditScreen;
import edu.wpi.cs3733.c20.teamS.Editing.tools.GraphEditor;
import edu.wpi.cs3733.c20.teamS.Editing.tools.IEditingTool;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Supplier;

public class AddNodeTool implements IEditingTool {
    private final GraphEditor graph;
    private final Supplier<Integer> currentFloorSupplier;

    public AddNodeTool(GraphEditor graph, Supplier<Integer> currentfloorSupplier) {
        this.graph = graph;
        this.currentFloorSupplier = currentfloorSupplier;
    }

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
                        e.value().setFloor(currentFloorSupplier.get());

                        graph.addNode(e.value());
                    }
                    stage.close();
                });
    }

    private String generateUniqueID(NodeData node) {
        Optional<Integer> max = graph.nodes().stream()
                .filter(n -> n.getFloor() == currentFloorSupplier.get())
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
