package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.Editing.NodeEditScreen;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.stage.Stage;

import java.util.Optional;

final class AddNodeTool extends EditingTool {
    private final GraphEditor graphEditor;

    public AddNodeTool(GraphEditor graphEditor) {
        this.graphEditor = graphEditor;
    }

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
                        e.value().setFloor(graphEditor.floorNumber());

                        graphEditor.database.addNode(e.value());
                        graphEditor.graph.addNode(e.value());

                        graphEditor.nodeAdded.onNext(e.value());
                    }
                    stage.close();
                });
    }

    private String generateUniqueID(NodeData node) {
        Optional<Integer> max = graphEditor.graph.nodes().stream()
                .filter(n -> n.getFloor() == graphEditor.floorNumber())
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
