package edu.wpi.cs3733.c20.teamS.Editing.tools;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Hitbox;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AddRemoveRoomEntrancesTool implements IEditingTool {
    private final Map<String, NodeData> nodeLookup;
    private final Supplier<Group> groupSupplier;

    private State state;

    public AddRemoveRoomEntrancesTool(
            Set<NodeData> nodes,
            Supplier<Group> groupSupplier
    ) {
        if (nodes == null) ThrowHelper.illegalNull("nodes");
        if (groupSupplier == null) ThrowHelper.illegalNull("groupSupplier");

        this.nodeLookup = nodes.stream()
                .collect(Collectors.toMap(node -> node.getNodeID(), node -> node));
        this.groupSupplier = groupSupplier;

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

    private abstract static class State  {
        public abstract void onHitboxClicked(Hitbox hitbox, MouseEvent event);
        public abstract void onNodeClicked(NodeData node, MouseEvent event);
    }

    private final class StandbyState extends State {
        @Override public void onHitboxClicked(Hitbox hitbox, MouseEvent event) {
            state = new EditingHitboxState(hitbox);
        }
        @Override public void onNodeClicked(NodeData node, MouseEvent event) {}
    }
    private final class EditingHitboxState extends State {
        private final Hitbox hitbox;
        private final Map<NodeData, Circle> highlighters;
        private final Group group;

        public EditingHitboxState(Hitbox hitbox) {
            if (hitbox == null) ThrowHelper.illegalNull("hitbox");

            this.hitbox = hitbox;
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
