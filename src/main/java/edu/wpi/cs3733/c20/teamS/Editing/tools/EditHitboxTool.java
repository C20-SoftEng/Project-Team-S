package edu.wpi.cs3733.c20.teamS.Editing.tools;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Hitbox;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.widgets.AutoComplete;
import io.reactivex.rxjava3.core.Observable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EditHitboxTool implements IEditingTool {
    private static class UI {
        private final JFXTextField nameField;
        private final VBox vbox;

        public UI(VBox vbox) {
            this.vbox = vbox;
            Label label = new Label();
            label.setText("Hitbox name: ");
            Insets insets = new Insets(8, 5, 8, 5);
            label.setPadding(insets);
            nameField = new JFXTextField();
            nameField.setPadding(insets);
            vbox.getChildren().addAll(label, nameField);
        }

        public String name() {
            return nameField.getText();
        }
        public void setName(String value) {
            nameField.setText(value);
        }
        public Observable<String> nameChanged() {
            return AutoComplete.propertyStream(nameField.textProperty());
        }
        public void clear() {
            vbox.getChildren().clear();
        }
    }

    private final Map<String, NodeData> nodeLookup;
    private final Supplier<Group> groupSupplier;
    private final UI ui;
    private State state;

    public EditHitboxTool(
            Set<NodeData> nodes,
            Supplier<Group> groupSupplier,
            VBox vbox
    ) {
        if (nodes == null) ThrowHelper.illegalNull("nodes");
        if (groupSupplier == null) ThrowHelper.illegalNull("groupSupplier");
        if (vbox == null) ThrowHelper.illegalNull("vbox");

        this.nodeLookup = nodes.stream()
                .collect(Collectors.toMap(node -> node.getNodeID(), node -> node));
        this.groupSupplier = groupSupplier;
        ui = new UI(vbox);
        ui.nameChanged().subscribe(name -> state.onNameChanged(name));

        state = new StandbyState();
    }

    @Override
    public void onHitboxClicked(Hitbox hitbox, MouseEvent event) {
        state.onHitboxClicked(hitbox, event);
    }

    @Override
    public void onNodeClicked(NodeData node, MouseEvent event) {
        state.onNodeClicked(node, event);
    }

    @Override
    public void onClosed() {
        state.onClosed();
        ui.clear();
    }

    private abstract static class State  {
        public abstract void onHitboxClicked(Hitbox hitbox, MouseEvent event);
        public abstract void onNodeClicked(NodeData node, MouseEvent event);
        public abstract void onClosed();
        public abstract void onNameChanged(String name);
    }

    private final class StandbyState extends State {
        @Override public void onHitboxClicked(Hitbox hitbox, MouseEvent event) {
            state = new EditingHitboxState(hitbox);
        }
        @Override public void onNodeClicked(NodeData node, MouseEvent event) {}
        @Override public void onClosed() {}
        @Override public void onNameChanged(String name) {}
    }
    private final class EditingHitboxState extends State {
        private final Hitbox hitbox;
        private final Map<NodeData, Circle> highlighters;
        private final Group group;
        private boolean respondToNameChanged = true;

        public EditingHitboxState(Hitbox hitbox) {
            if (hitbox == null) ThrowHelper.illegalNull("hitbox");

            this.hitbox = hitbox;
            ui.setName(hitbox.name());
            highlighters = new HashMap<>();
            group = groupSupplier.get();
            hitbox.touchingNodes().removeIf(id -> !nodeLookup.containsKey(id));
            hitbox.touchingNodes().stream()
                    .map(nodeLookup::get)
                    .forEach(this::addNodeHighlighter);
        }

        @Override public void onHitboxClicked(Hitbox hitbox, MouseEvent event) {
            if (hitbox == this.hitbox)
                return;
            group.getChildren().removeAll(highlighters.values());
            highlighters.clear();
            respondToNameChanged = false;
            state = new EditingHitboxState(hitbox);
        }
        @Override public void onNodeClicked(NodeData node, MouseEvent event) {
            switch (event.getButton()) {
                case PRIMARY:
                    if (hitbox.touchingNodes().add(node.getNodeID()))
                        addNodeHighlighter(node);
                    break;
                case SECONDARY:
                    removeNode(node);
                    break;
                default:
                    break;
            }
        }
        @Override public void onClosed() {
            group.getChildren().removeAll(highlighters.values());
        }
        @Override public void onNameChanged(String name) {
            if (!respondToNameChanged)
                return;
            hitbox.setName(name);
        }

        private Circle createNodeHighlighter(NodeData node) {
            Circle circle = new Circle();
            circle.setFill(Color.LIME);
            circle.setRadius(20);
            circle.setCenterX(node.getxCoordinate());
            circle.setCenterY(node.getyCoordinate());
            circle.setMouseTransparent(true);
            return circle;
        }

        private void addNodeHighlighter(NodeData node) {
            Circle circle = createNodeHighlighter(node);
            group.getChildren().add(circle);
            highlighters.put(node, circle);
        }

        private void removeNode(NodeData node) {
            if (!hitbox.touchingNodes().remove(node.getNodeID()))
                return;
            Circle circle = highlighters.get(node);
            group.getChildren().remove(circle);
            highlighters.remove(node);
        }
    }
}
