package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.NodeEditScreen;
import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Set;
import java.util.function.Consumer;

public final class AddRemoveNodeTool extends EditingTool {
    private final IEditableMap map;
    private String previousNodeType = "HALL";
    private String previousShortName = "NA";
    private String previousLongName = "Unnamed";

    public AddRemoveNodeTool(Consumer<Memento> mementoRunner, IEditableMap map) {
        super(mementoRunner);

        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        addAllSubs(
                map.mapClicked().subscribe(this::onMapClicked),
                map.nodeClicked().subscribe(this::onNodeClicked)
        );
    }

    private void onNodeClicked(NodeClickedEvent event) {
        if (event.event().getButton() != MouseButton.SECONDARY)
            return;

        Memento action = new Memento() {
            private final NodeData node = event.node().node();
            private final Set<NodeData> friends = map.graph().inner().adjacentNodes(node);

            @Override public void execute() {
                map.removeNode(node);
            }
            @Override public void undo() {
                map.addNode(node);
                for (NodeData friend : friends)
                    map.putEdge(node, friend);
            }
        };
        execute(action);
    }

    private void onMapClicked(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY)
            return;

        Stage stage = new Stage();
        NodeEditScreen.showDialog(
                stage, previousNodeType,
                previousShortName,
                previousLongName
        )
                .subscribe(e -> {
                    if (e.result() == DialogResult.OK) {
                        e.value().setBuilding("Faulkner");
                        e.value().setxCoordinate(event.getX());
                        e.value().setyCoordinate(event.getY());
                        e.value().setFloor(map.selectedFloor());

                        execute(
                                () -> map.addNode(e.value()),
                                () -> map.removeNode(e.value())
                        );

                        previousNodeType = e.value().getNodeType();
                        previousShortName = e.value().getShortName();
                        previousLongName = e.value().getLongName();
                    }
                    stage.close();
                });
    }
}
