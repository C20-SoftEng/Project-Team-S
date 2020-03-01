package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.function.Consumer;

public final class MoveNodeTool extends EditingTool {
    private final IEditableMap map;
    private final boolean wasInitiallyPannable;

    public MoveNodeTool(Consumer<Memento> mementoRunner, IEditableMap map) {
        super(mementoRunner);

        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        this.wasInitiallyPannable = map.isPannable();

        addAllSubs(
                map.nodeDragged().subscribe(this::onNodeDragged),
                map.nodeReleased().subscribe(this::onNodeReleased)
        );
    }

    private void onNodeDragged(NodeClickedEvent e) {
        map.setPannable(false);
        e.node().node().setPosition(e.event().getX(), e.event().getY());
        e.event().consume();
    }
    private void onNodeReleased(NodeClickedEvent e) {
        map.setPannable(wasInitiallyPannable);
    }
}
