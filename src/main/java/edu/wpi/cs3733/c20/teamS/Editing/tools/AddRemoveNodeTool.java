package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.NodeEditScreen;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Supplier;

public final class AddRemoveNodeTool implements IEditingTool {
    private final ObservableGraph graph;
    private final Supplier<Integer> currentFloorSupplier;

    public AddRemoveNodeTool(ObservableGraph graph, Supplier<Integer> currentfloorSupplier) {
        this.graph = graph;
        this.currentFloorSupplier = currentfloorSupplier;
    }

    @Override
    public void onNodeClicked(NodeData node, MouseEvent event) {
        if (event.getButton() != MouseButton.SECONDARY)
            return;

        graph.removeNode(node);
    }

    @Override
    public void onMapClicked(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        Stage stage = new Stage();
        NodeEditScreen.showDialog(stage)
                .subscribe(e -> {
                    if (e.result() == DialogResult.OK) {
                        e.value().setBuilding("Faulkner");
                        e.value().setxCoordinate(event.getX());
                        e.value().setyCoordinate(event.getY());
                        e.value().setFloor(currentFloorSupplier.get());

                        graph.addNode(e.value());
                    }
                    stage.close();
                });
    }
}
