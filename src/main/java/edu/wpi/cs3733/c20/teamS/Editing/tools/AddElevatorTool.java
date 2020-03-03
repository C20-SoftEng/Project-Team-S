package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;

import java.util.function.Consumer;

public final class AddElevatorTool extends EditingTool {
    private final IEditableMap map;
    private final int topFloor;

    public AddElevatorTool(Consumer<Memento> mementoRunner, IEditableMap map, int topFloor) {
        super(mementoRunner);
        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        this.topFloor = topFloor;

        addAllSubs(
                map.nodeClicked().subscribe(this::onNodeClicked)
        );
    }

    private void onNodeClicked(NodeClickedEvent data) {
        NodeData base = data.node().node();
        int newFloor = 0;
        switch (data.event().getButton()) {
            case PRIMARY:
                newFloor = base.getFloor() + 1;
                break;
            case SECONDARY:
                newFloor = base.getFloor() - 1;
                break;
            default:
                return;
        }
        if (!isFloorInRange(newFloor))
            return;

        NodeData top = createElevatorNode(base, newFloor);

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
        map.setSelectedFloor(newFloor);
    }

    private boolean isFloorInRange(int floor) {
        return floor >= 1 && floor <= topFloor;
    }
    private NodeData createElevatorNode(NodeData base, int newFloor) {
        assert base != null : "'base' is null.";
        NodeData result = new NodeData();
        result.setFloor(newFloor);
        result.setNodeType("ELEV");

        result.setPosition(base.getxCoordinate(), base.getyCoordinate());
        result.setBuilding(base.getBuilding());
        result.setShortName(base.getShortName());
        result.setLongName(base.getLongName());

        return result;
    }
}
