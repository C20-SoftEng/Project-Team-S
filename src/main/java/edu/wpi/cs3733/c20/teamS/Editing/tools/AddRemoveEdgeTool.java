package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.events.EdgeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.PlaceEdgeVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableBase;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableSelector;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

public final class AddRemoveEdgeTool extends EditingTool {
    private final IEditableMap map;
    private final DisposableSelector<State> state = new DisposableSelector<>();

    public AddRemoveEdgeTool(Consumer<Memento> mementoRunner, IEditableMap map) {
        super(mementoRunner);

        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        state.setCurrent(new StandbyState());

        addAllSubs(
                map.nodeClicked().subscribe(e -> state.current().onNodeClicked(e)),
                map.edgeClicked().subscribe(e -> state.current().onEdgeClicked(e)),
                map.mouseMoved().subscribe(e -> state.current().onMouseMoved(e))
        );
    }

    @Override protected void onDispose() {
        state.current().dispose();
    }

    private static abstract class State extends DisposableBase {
        public abstract void onNodeClicked(NodeClickedEvent data);
        public abstract void onEdgeClicked(EdgeClickedEvent data);
        public void onMouseMoved(MouseEvent event) {}
        @Override protected void onDispose() {}
    }

    private final class StandbyState extends State {
        @Override public void onNodeClicked(NodeClickedEvent data) {
            if (data.event().getButton() != MouseButton.PRIMARY)
                return;

            state.setCurrent(new StartPlacedState(data.node().node()));
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
            switch (data.event().getButton()) {
                case SECONDARY:
                    state.setCurrent(new StandbyState());
                    return;
                case PRIMARY:
                    if (!data.node().node().equals(start)) {
                        NodeData end = data.node().node();
                        execute(
                                () -> map.putEdge(start, end),
                                () -> map.removeEdge(start, end)
                        );
                    }
                    map.removeWidget(vm);
                    state.setCurrent(new StandbyState());
                    return;
                default:
            }
        }
        @Override protected void onDispose() {
            map.removeWidget(vm);
        }
        @Override public void onEdgeClicked(EdgeClickedEvent data) { }
        @Override public void onMouseMoved(MouseEvent event) {
            vm.setEnd(event.getX(), event.getY());
        }
    }
}
