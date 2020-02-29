package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.events.RoomClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.PlaceVertexHandleVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.PreviewHitboxVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import edu.wpi.cs3733.c20.teamS.utilities.rx.RxAdaptors;
import io.reactivex.rxjava3.disposables.Disposable;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Stack;

public class AddRemoveHitboxTool extends EditingTool {
    private final IEditableMap map;
    private State state;

    public AddRemoveHitboxTool(IEditableMap map) {
        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        state = new StandbyState();

        addAllSubs(
                map.mapClicked().subscribe(e -> state.onMapClicked(e)),
                map.mouseMoved().subscribe(e -> state.onMouseMoved(e)),
                map.roomClicked().subscribe(e -> state.onRoomClicked(e))
        );
    }

    @Override
    protected void onDispose() {
        state.dispose();
    }

    private static abstract class State implements Disposable {
        private boolean isDisposed = false;

        public void onMapClicked(MouseEvent event) {}
        public void onMouseMoved(MouseEvent event) {}
        public void onRoomClicked(RoomClickedEvent data) {}
        public final void dispose() {
            if (isDisposed)
                return;
            isDisposed = true;
            onDispose();
        }
        protected void onDispose() {}
        public final boolean isDisposed() {
            return isDisposed;
        }
    }
    private final class StandbyState extends State {
        @Override public void onMapClicked(MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY)
                return;

            state = new PlacingState(event.getX(), event.getY());
        }
        @Override public void onRoomClicked(RoomClickedEvent data) {
            if (data.event().getButton() != MouseButton.SECONDARY)
                return;
            map.removeRoom(data.room().room());
        }
    }

    private final class PlacingState extends State {
        private final PreviewHitboxVm preview;
        private final Room room = new Room();
        private final Stack<PlaceVertexHandleVm> handles = new Stack<>();

        public PlacingState(double x, double y) {
            preview = new PreviewHitboxVm(x, y);
            pushVertex(x, y);
            pushVertex(x, y);
            map.addWidget(preview);
        }

        @Override public void onMapClicked(MouseEvent event) {
            switch (event.getButton()) {
                case PRIMARY:
                    preview.setLastVertex(event.getX(), event.getY());
                    preview.pushVertex(event.getX(), event.getY());
                    pushVertex(event.getX(), event.getY());
                    break;
                case SECONDARY:
                    goToStandbyState();
                    break;
                default:
                    break;
            }
        }

        @Override public void onMouseMoved(MouseEvent event) {
            preview.setLastVertex(event.getX(), event.getY());
            room.setLastVertex(event.getX(), event.getY());
            if (!handles.isEmpty()) {
                handles.peek().setTranslateX(event.getX());
                handles.peek().setTranslateY(event.getY());
            }
        }

        @Override
        protected void onDispose() {
            map.removeWidget(preview);
            for (Node node : handles)
                map.removeWidget(node);
        }

        private void pushVertex(double x, double y) {
            room.vertices().add(new Vector2(x, y));

            if (!handles.isEmpty())
                handles.peek().setMouseTransparent(false);

            PlaceVertexHandleVm handle = new PlaceVertexHandleVm(x, y);
            handle.setMouseTransparent(true);
            RxAdaptors.eventStream(handle::setOnMouseClicked).subscribe(e -> onVertexHandleClicked(handle, e));
            handles.push(handle);
            map.addWidget(handle);
        }
        private void onVertexHandleClicked(PlaceVertexHandleVm handle, MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY)
                return;

            event.consume();
            map.addRoom(room);

            goToStandbyState();
        }
        private void goToStandbyState() {
            dispose();
            state = new StandbyState();
        }
    }
}
