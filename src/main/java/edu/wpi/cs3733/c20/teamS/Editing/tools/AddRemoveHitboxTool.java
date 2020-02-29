package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.viewModels.PreviewHitboxVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.scene.input.MouseEvent;

public class AddRemoveHitboxTool extends EditingTool {
    private final IEditableMap map;
    private State state;

    public AddRemoveHitboxTool(IEditableMap map) {
        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        state = new StandbyState();

        addAllSubs(
                map.mapClicked().subscribe(e -> state.onMapClicked(e)),
                map.mouseMoved().subscribe(e -> state.onMouseMoved(e))
        );
    }

    private static abstract class State {
        public void onMapClicked(MouseEvent event) {}
        public void onMouseMoved(MouseEvent event) {}
    }
    private final class StandbyState extends State {
        @Override public void onMapClicked(MouseEvent event) {
            state = new PlacingState(event.getX(), event.getY());
        }
    }
    private final class PlacingState extends State {
        private final PreviewHitboxVm preview;

        public PlacingState(double x, double y) {
            preview = new PreviewHitboxVm(x, y);
            map.addWidget(preview);
        }

        @Override public void onMapClicked(MouseEvent event) {
            double x = event.getX();
            double y = event.getY();
            preview.setLastVertex(x, y);
            preview.pushVertex(x, y);
        }

        @Override public void onMouseMoved(MouseEvent event) {
            preview.setLastVertex(event.getX(), event.getY());
        }
    }
}
