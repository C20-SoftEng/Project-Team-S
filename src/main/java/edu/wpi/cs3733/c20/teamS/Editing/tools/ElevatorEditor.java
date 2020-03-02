package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import java.util.function.Consumer;

public final class ElevatorEditor extends EditingTool {
    private final IEditableMap map;
    private final int topFloor;

    public ElevatorEditor(Consumer<Memento> mementoRunner, IEditableMap map, int topFloor) {
        super(mementoRunner);
        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        this.topFloor = topFloor;

        addAllSubs(
                map.nodeClicked().subscribe(this::onNodeClicked)
        );
    }

    private void onNodeClicked(NodeClickedEvent data) {
        if (map.selectedFloor() >= topFloor)
            return;

        NodeData base = data.node().node();
        NodeData top = createElevatorNode(base);

        execute(
                () -> {
                    map.addNode(top);
                    map.putEdge(base, top);
                },
                () -> {
                    map.removeEdge(base, top);
                    map.removeNode(top);
                }
        );
        map.setSelectedFloor(top.getFloor());
    }

    private NodeData createElevatorNode(NodeData base) {
        assert base != null : "'base' is null.";
        NodeData result = new NodeData();
        result.setFloor(base.getFloor() + 1);
        result.setNodeType("ELEV");

        result.setPosition(base.getxCoordinate(), base.getyCoordinate());
        result.setBuilding(base.getBuilding());
        result.setShortName(base.getShortName());
        result.setLongName(base.getLongName());

        return result;
    }
}
