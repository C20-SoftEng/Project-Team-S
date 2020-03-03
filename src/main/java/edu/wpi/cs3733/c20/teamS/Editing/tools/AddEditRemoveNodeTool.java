package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.Editing.NodeEditScreen;
import edu.wpi.cs3733.c20.teamS.Editing.events.NodeClickedEvent;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableBase;
import edu.wpi.cs3733.c20.teamS.utilities.rx.DisposableSelector;
import io.reactivex.rxjava3.disposables.Disposable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class AddEditRemoveNodeTool extends EditingTool {
    private final IEditableMap map;
    private final DisposableSelector<State> state = new DisposableSelector<>();
    private String previousNodeType = "HALL";
    private String previousShortName = "NA";
    private String previousLongName = "Unnamed";

    public AddEditRemoveNodeTool(Consumer<Memento> mementoRunner, IEditableMap map) {
        super(mementoRunner);

        if (map == null) ThrowHelper.illegalNull("map");

        this.map = map;
        state.setCurrent(new StandbyState());

        addAllSubs(
                map.mapClicked().subscribe(e -> state.current().onMapClicked(e)),
                map.nodeClicked().subscribe(e -> state.current().onNodeClicked(e))
        );
    }

    private static abstract class State extends DisposableBase {
        public void onNodeClicked(NodeClickedEvent data) {}
        public void onMapClicked(MouseEvent event) {}
        @Override protected void onDispose() { }
    }

    private final class StandbyState extends State {
        @Override public void onNodeClicked(NodeClickedEvent event) {
            switch (event.event().getButton()) {
                case PRIMARY:
                    state.setCurrent(new ShowEditNodeDialogState(event.node().node()));
                    break;
                case SECONDARY:
                    Memento action = createRemoveNodeMemento(event);
                    execute(action);
                    break;
            }
        }

        @Override public void onMapClicked(MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY)
                return;

            state.setCurrent(new ShowNewNodeDialogState(event.getX(), event.getY()));
        }

        private Memento createRemoveNodeMemento(NodeClickedEvent event) {
            return new Memento() {
                private final NodeData node = event.node().node();
                private final Set<NodeData> friends = map.graph().inner().adjacentNodes(node);

                @Override public void execute() {
                    map.removeNode(node);
                }
                @Override public void undo() {
                    map.addNode(node);
                    for (NodeData friend : friends)
                        map.putEdge(node, friend);
                }
            };
        }
    }

    private final class ShowNewNodeDialogState extends State {
        private final Stage stage;
        private final Disposable dialogSubscription;

        public ShowNewNodeDialogState(double x, double y) {
            stage = new Stage();
            dialogSubscription = showDialog(x, y);
        }

        @Override protected void onDispose() {
            dialogSubscription.dispose();
            stage.close();
        }

        private Disposable showDialog(double x, double y) {
            return NodeEditScreen.showDialog(
                    stage, previousNodeType,
                    previousShortName,
                    previousLongName
            ).subscribe(e -> {
                if (e.result() == DialogResult.OK) {
                    e.value().setBuilding("Faulkner");
                    e.value().setxCoordinate(x);
                    e.value().setyCoordinate(y);
                    e.value().setFloor(map.selectedFloor());

                    execute(
                            () -> map.addNode(e.value()),
                            () -> map.removeNode(e.value())
                    );

                    previousNodeType = e.value().getNodeType();
                    previousShortName = e.value().getShortName();
                    previousLongName = e.value().getLongName();
                }

                state.setCurrent(new StandbyState());
            });
        }
    }

    private final class ShowEditNodeDialogState extends State {
        private final Stage stage;
        private final Disposable dialogSubscription;

        public ShowEditNodeDialogState(NodeData node) {
            if (node == null) ThrowHelper.illegalNull("node");

            stage = new Stage();
            dialogSubscription = showDialog(node);
        }

        @Override protected void onDispose() {
            dialogSubscription.dispose();
            stage.close();
        }

        private Disposable showDialog(NodeData node) {
            NodeState before = new NodeState(node);
            return NodeEditScreen.showDialog(stage, node)
                    .subscribe(e -> {
                        if (e.result() == DialogResult.OK) {
                            NodeState after = new NodeState(e.value());
                            Memento memento = Memento.create(
                                    () -> {},
                                    () -> before.setNodeFromState(node),
                                    () -> after.setNodeFromState(node)
                            );
                            execute(memento);
                        }
                        state.setCurrent(new StandbyState());
                    });
        }

        private class NodeState {
            private final Map<String, Object>  fieldValues = new HashMap<>();

            public NodeState(NodeData node) {
                try {
                    for (Field field : getMutableInstanceFields()) {
                        field.setAccessible(true);
                        fieldValues.put(field.getName(), field.get(node));
                    }
                }
                catch (IllegalAccessException ex) {
                    throw new RuntimeException("Illegal read-access of field on NodeData using reflection.", ex);
                }
            }

            public void setNodeFromState(NodeData node) {
                try {
                    Field[] fields = node.getClass().getDeclaredFields();
                    for (Field field : getMutableInstanceFields()) {
                        field.setAccessible(true);
                        String name = field.getName();
                        assert fieldValues.containsKey(name) : "Field map doesn't contain '" + name + "' field.";
                        Object value = fieldValues.get(name);
                        field.set(node, value);
                    }
                }
                catch (IllegalAccessException ex) {
                    throw new RuntimeException("Illegal write-access of field on NodeData using reflection.", ex);
                }
            }

            private List<Field> getMutableInstanceFields() {
                return Arrays.stream(NodeData.class.getDeclaredFields())
                        .filter(field -> !Modifier.isStatic(field.getModifiers()))
                        .filter(field -> !Modifier.isFinal(field.getModifiers()))
                        .collect(Collectors.toList());
            }
        }
    }
}
