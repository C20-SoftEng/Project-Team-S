package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.NodeEditScreen;
import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public final class AddRemoveNodeTool extends EditingTool {
    private final IEditableMap map;
    private String previousNodeType = "HALL";
    private String previousShortName = "NA";
    private String previousLongName = "Unnamed";

    public AddRemoveNodeTool(IEditableMap map) {
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

        map.removeNode(event.node().node());
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
                        map.addNode(e.value());

                        previousNodeType = e.value().getNodeType();
                        previousShortName = e.value().getShortName();
                        previousLongName = e.value().getLongName();
                    }
                    stage.close();
                });
    }
}
