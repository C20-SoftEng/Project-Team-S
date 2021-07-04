package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.events.RoomClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.PlaceVertexHandleVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.PreviewRoomVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableBase;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableSelector;
import edu.wpi.cs3733.c20.teamS.utilities.rx.RxAdaptors;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Stack;
import java.util.function.Consumer;

public final class AddRemoveRoomTool extends EditingTool {
    private final IEditableMap map;
    private final DisposableSelector<State> state = new DisposableSelector<>();

    public AddRemoveRoomTool(Consumer<Memento> mementoRunner, IEditableMap map) {
        super(mementoRunner);

        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        state.setCurrent(new StandbyState());
        addAllSubs(
                map.mapClicked().subscribe(e -> state.current().onMapClicked(e)),
                map.mouseMoved().subscribe(e -> state.current().onMouseMoved(e)),
                map.roomClicked().subscribe(e -> state.current().onRoomClicked(e))
        );
    }

    @Override protected final void onDispose() {
        state.current().dispose();
    }

    private static abstract class State extends DisposableBase {
        public void onMapClicked(MouseEvent event) {}
        public void onMouseMoved(MouseEvent event) {}
        public void onRoomClicked(RoomClickedEvent data) {}
        protected void onDispose() {}
    }

    private final class StandbyState extends State {
        @Override public void onMapClicked(MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY)
                return;

            state.setCurrent(new PlacingState(event.getX(), event.getY()));
        }
        @Override public void onRoomClicked(RoomClickedEvent data) {
            if (data.event().getButton() != MouseButton.SECONDARY)
                return;

            execute(
                    () -> map.removeRoom(data.room().room()),
                    () -> map.addRoom(data.room().room())
            );
        }
    }

    private final class PlacingState extends State {
        private final PreviewRoomVm preview;
        private final Room room = new Room();
        private final Stack<PlaceVertexHandleVm> handles = new Stack<>();

        public PlacingState(double x, double y) {
            preview = new PreviewRoomVm(x, y);
            pushVertex(x, y);
            pushVertex(x, y);
            map.addWidget(preview);
            room.setFloor(map.selectedFloor());
        }

        @Override public void onMapClicked(MouseEvent event) {
            switch (event.getButton()) {
                case PRIMARY:
                    preview.setLastVertex(event.getX(), event.getY());
                    preview.pushVertex(event.getX(), event.getY());
                    pushVertex(event.getX(), event.getY());
                    break;
                case SECONDARY:
                    state.setCurrent(new StandbyState());
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
        @Override protected void onDispose() {
            map.removeWidget(preview);
            for (Node node : handles)
                map.removeWidget(node);
        }

        private void pushVertex(double x, double y) {
            room.vertices().add(new Vector2(x, y));

            if (!handles.isEmpty()) {
                handles.peek().setMouseTransparent(false);
                handles.peek().setVisible(true);
                handles.peek().setTranslateX(x);
                handles.peek().setTranslateY(y);
            }

            PlaceVertexHandleVm handle = new PlaceVertexHandleVm(x, y);
            handle.setMouseTransparent(true);
            handle.setVisible(false);
            RxAdaptors.eventStream(handle::setOnMouseClicked).subscribe(e -> onVertexHandleClicked(handle, e));
            handles.push(handle);
            map.addWidget(handle);
        }
        private void onVertexHandleClicked(PlaceVertexHandleVm handle, MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY)
                return;

            event.consume();

            execute(
                    () -> map.addRoom(room),
                    () -> map.removeRoom(room)
            );

            state.setCurrent(new StandbyState());
        }
    }
}
