package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.NodeVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableBase;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableSelector;

import java.util.function.Consumer;

public final class MoveNodeTool extends EditingTool {
    private final IEditableMap map;
    private final DisposableSelector<State> state = new DisposableSelector<>();

    public MoveNodeTool(Consumer<Memento> mementoRunner, IEditableMap map) {
        super(mementoRunner);

        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;

        addAllSubs(
                map.nodeDragged().subscribe(this::onNodeDragged),
                map.nodeReleased().subscribe(this::onNodeReleased)
        );
        state.setCurrent(new StandbyState());
    }

    private void onNodeDragged(NodeClickedEvent e) {
        state.current().onNodeDragged(e);
    }
    private void onNodeReleased(NodeClickedEvent e) {
        state.current().onNodeReleased(e);
    }

    private static abstract class State extends DisposableBase {
        public void onNodeDragged(NodeClickedEvent data) {}
        public void onNodeReleased(NodeClickedEvent data) {}
        @Override protected void onDispose() {}
    }

    private final class StandbyState extends State {
        @Override public void onNodeDragged(NodeClickedEvent data) {
            state.setCurrent(new DraggingState(data.node()));
        }
    }
    private final class DraggingState extends State {
        private final NodeVm nodeVm;
        private final double startX, startY;

        public DraggingState(NodeVm nodeVm) {
            this.startX = nodeVm.node().getxCoordinate();
            this.startY = nodeVm.node().getyCoordinate();
            this.nodeVm = nodeVm;
            map.setPannable(false);
        }

        @Override public void onNodeDragged(NodeClickedEvent data) {
            nodeVm.node().setPosition(data.parentPosition().x(), data.parentPosition().y());
        }
        @Override public void onNodeReleased(NodeClickedEvent data) {
            double x = data.parentPosition().x();
            double y = data.parentPosition().y();
            execute(
                    () -> data.node().node().setPosition(x, y),
                    () -> data.node().node().setPosition(startX, startY)
            );
            map.setPannable(true);
            state.setCurrent(new StandbyState());
        }
    }
}
