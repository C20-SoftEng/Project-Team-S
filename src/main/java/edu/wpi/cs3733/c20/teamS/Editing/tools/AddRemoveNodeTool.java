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

import java.util.Set;
import java.util.function.Consumer;

public final class AddRemoveNodeTool extends EditingTool {
    private final IEditableMap map;
    private final DisposableSelector<State> state = new DisposableSelector<>();
    private String previousNodeType = "HALL";
    private String previousShortName = "NA";
    private String previousLongName = "Unnamed";

    public AddRemoveNodeTool(Consumer<Memento> mementoRunner, IEditableMap map) {
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
            if (event.event().getButton() != MouseButton.SECONDARY)
                return;

            Memento action = createRemoveNodeMemento(event);
            execute(action);
        }

        @Override public void onMapClicked(MouseEvent event) {
            if (event.getButton() != MouseButton.PRIMARY)
                return;

            state.setCurrent(new ShowDialogState(event.getX(), event.getY()));
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

    private final class ShowDialogState extends State {
        private final Stage stage;
        private final Disposable dialogSubscription;

        public ShowDialogState(double x, double y) {
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
}
