package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.events.RoomClickedEvent;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.EditRoomVertexVm;
import edu.wpi.cs3733.c20.teamS.Editing.viewModels.NodeVm;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableBase;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableSelector;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EditRoomTool extends EditingTool {
    private final IEditableMap map;
    private final DisposableSelector<State> state = new DisposableSelector<>();
    private final Map<String, NodeData> nodeLookup;

    public EditRoomTool(Consumer<Memento> mementoRunner, IEditableMap map) {
        super(mementoRunner);
        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        state.setCurrent(new StandbyState());
        nodeLookup = map.graph().nodes().stream()
                .collect(Collectors.toMap(NodeData::getNodeID, node -> node));

        addAllSubs(
                map.roomClicked().subscribe(e -> state.current().onRoomClicked(e)),
                map.nodeClicked().subscribe(e -> state.current().onNodeClicked(e))
        );
    }

    @Override
    protected void onDispose() {
        state.current().dispose();
    }

    private static abstract class State extends DisposableBase {
        public void onRoomClicked(RoomClickedEvent data) {}
        public void onNodeClicked(NodeClickedEvent data) {}
        @Override protected void onDispose() {}
    }

    private final class StandbyState extends State {
        @Override public void onRoomClicked(RoomClickedEvent data) {
            state.setCurrent(new RoomSelectedState(data.room().room()));
        }
    }

    private final class RoomSelectedState extends State {
        private final List<EditRoomVertexVm> handles = new ArrayList<>();
        private final Set<NodeVm> touchingNodeVms = new HashSet<>();
        private HandleState handleState;

        public RoomSelectedState(Room room) {
            handleState = new NotDraggingState();

            initRoomVertexHandles(room);

            room.touchingNodes().stream()
                    .map(nodeLookup::get)
                    .map(map::getNodeViewModel)
                    .forEach(vm -> {

                    });
        }

        private void initRoomVertexHandles(Room room) {
            for (int index = 0; index < room.vertices().size(); index++) {
                EditRoomVertexVm handle = new EditRoomVertexVm(room, index);
                handle.dragged().subscribe(v -> handleState.onDrag(handle, v));
                handle.released().subscribe(v -> handleState.onRelease(handle, v));
                handles.add(handle);
                map.addWidget(handle);
            }
        }

        @Override protected void onDispose() {
            handles.forEach(map::removeWidget);
        }

        @Override public void onRoomClicked(RoomClickedEvent data) {
            state.setCurrent(new RoomSelectedState(data.room().room()));
        }

        private abstract class HandleState {
            public void onDrag(EditRoomVertexVm handle, Vector2 mouse) {}
            public void onRelease(EditRoomVertexVm handle, Vector2 mouse) {}
        }

        private final class NotDraggingState extends HandleState {
            @Override public void onDrag(EditRoomVertexVm handle, Vector2 mouse) {
                handleState = new DraggingState(handle, mouse);
            }
        }

        private final class DraggingState extends HandleState {
            private final Vector2 start;

            public DraggingState(EditRoomVertexVm handle, Vector2 mouse) {
                map.setPannable(false);
                this.start = handle.room().vertices().get(handle.vertexIndex());
                onDrag(handle, mouse);
            }

            @Override public void onDrag(EditRoomVertexVm handle, Vector2 mouse) {
                handle.setTranslateX(mouse.x());
                handle.setTranslateY(mouse.y());
                handle.room().vertices().set(handle.vertexIndex(), mouse);
            }
            @Override public void onRelease(EditRoomVertexVm handle, Vector2 mouse) {
                map.setPannable(true);

                Room room = handle.room();
                int index = handle.vertexIndex();
                execute(
                        () -> room.vertices().set(index, mouse),
                        () -> room.vertices().set(index, start)
                );

                handleState = new NotDraggingState();
            }
        }
    }
}
