package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.events.EdgeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.PlaceEdgeVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

public final class AddRemoveEdgeTool extends EditingTool {
    private final IEditableMap map;
    private State state;

    public AddRemoveEdgeTool(Consumer<Memento> mementoRunner, IEditableMap map) {
        super(mementoRunner);

        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        this.state = new StandbyState();

        addAllSubs(
                map.nodeClicked().subscribe(e -> state.onNodeClicked(e)),
                map.edgeClicked().subscribe(e -> state.onEdgeClicked(e)),
                map.mouseMoved().subscribe(e -> state.onMouseMoved(e))
        );
    }

    private static abstract class State {
        public abstract void onNodeClicked(NodeClickedEvent data);
        public abstract void onEdgeClicked(EdgeClickedEvent data);
        public void onMouseMoved(MouseEvent event) {}
    }

    private final class StandbyState extends State {
        @Override public void onNodeClicked(NodeClickedEvent data) {
            state = new StartPlacedState(data.node().node());
        }
        @Override public void onEdgeClicked(EdgeClickedEvent data) {
            if (data.event().getButton() != MouseButton.SECONDARY)
                return;

            Memento action = Memento.create(
                    () -> map.removeEdge(data.edge().start(), data.edge().end()),
                    () -> map.putEdge(data.edge().start(), data.edge().end())
            );
            execute(action);
        }
    }
    private final class StartPlacedState extends State {
        private final NodeData start;
        private final PlaceEdgeVm vm;

        public StartPlacedState(NodeData start) {
            this.start = start;
            vm = new PlaceEdgeVm(start.getxCoordinate(), start.getyCoordinate());
            map.addWidget(vm);
        }

        @Override public void onNodeClicked(NodeClickedEvent data) {
            if (!data.node().node().equals(start)) {
                NodeData end = data.node().node();
                Memento action = Memento.create(
                        () -> map.putEdge(start, end),
                        () -> map.removeEdge(start, end)
                );
                execute(action);
            }
            map.removeWidget(vm);
            state = new StandbyState();
        }
        @Override public void onEdgeClicked(EdgeClickedEvent data) { }
        @Override public void onMouseMoved(MouseEvent event) {
            vm.setEnd(event.getX(), event.getY());
        }
    }
}
