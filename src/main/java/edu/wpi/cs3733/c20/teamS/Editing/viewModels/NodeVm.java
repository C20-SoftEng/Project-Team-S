package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.utilities.ReactiveProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NodeVm extends Parent {
    private final NodeData node;
    private final Circle visibleMask;
    private final Circle collisionMask;
    private static final Color INVISIBLE = Color.BLACK.deriveColor(0, 0, 0, 0);
    private final ReactiveProperty<Boolean> isMouseOver = new ReactiveProperty<>(false);
    private final ReactiveProperty<Boolean> highlightOnMouseOver = new ReactiveProperty<>(true);

    public NodeVm(NodeData node) {
        this.node = node;
        visibleMask = new Circle();
        visibleMask.setCenterX(node.getxCoordinate());
        visibleMask.setCenterY(node.getyCoordinate());
        visibleMask.setRadius(20);
        visibleMask.setMouseTransparent(true);
        visibleMask.setFill(Settings.get().nodeFillColorNormal());
        visibleMask.setStroke(Settings.get().nodeStrokeColorNormal());
        getChildren().add(visibleMask);

        collisionMask = new Circle();
        collisionMask.setCenterX(node.getxCoordinate());
        collisionMask.setCenterY(node.getyCoordinate());
        collisionMask.setRadius(35);
        collisionMask.setFill(INVISIBLE);
        collisionMask.setStroke(INVISIBLE);
        getChildren().add(collisionMask);

        collisionMask.setOnMouseEntered(e -> isMouseOver.setValue(true));
        collisionMask.setOnMouseExited(e -> isMouseOver.setValue(false));
        isMouseOver.changed().mergeWith(highlightOnMouseOver.changed())
                .subscribe(e -> updateHighlightState());

        isMouseOver.changed()
                .subscribe(huh -> visibleMask.setRadius(huh ? 30 : 20));
    }

    public boolean highlightOnMouseOver() {
        return highlightOnMouseOver.value();
    }
    public void setHighlightOnMouseOver(boolean value) {
        highlightOnMouseOver.setValue(value);
    }
    public double visibleRadius() {
        return visibleMask.getRadius();
    }
    public void setVisibleRadius(double value) {
        visibleMask.setRadius(value);
    }
    public double collisionRadius() {
        return collisionMask.getRadius();
    }
    public void setCollisionRadius(double value) {
        collisionMask.setRadius(value);
    }

    private void updateHighlightState() {
        if (highlightOnMouseOver.value() && isMouseOver.value())
            visibleMask.setFill(Settings.get().nodeFillColorHighlight());
        else
            visibleMask.setFill(Settings.get().nodeFillColorNormal());
    }
}
