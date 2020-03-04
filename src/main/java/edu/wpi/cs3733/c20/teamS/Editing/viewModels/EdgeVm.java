package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import com.google.common.graph.EndpointPair;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.utilities.rx.ReactiveProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public final class EdgeVm extends Parent {
    private final NodeData start;
    private final NodeData end;

    private final Line visibleMask;
    private final Circle startBall;
    private final Circle endBall;
    private final Line collisionMask;
    private final ReactiveProperty<Boolean> isMouseOver = new ReactiveProperty<>(false);
    private final ReactiveProperty<Boolean> highlightOnMouseOver = new ReactiveProperty<>(true);
    private final ReactiveProperty<Boolean> enlargeOnMouseOver = new ReactiveProperty<>(true);

    public EdgeVm(NodeData start, NodeData end) {
        if (start == null) ThrowHelper.illegalNull("start");
        if (end == null) ThrowHelper.illegalNull("end");

        this.start = start;
        this.end = end;

        visibleMask = new Line();
        updateLinePosition(visibleMask);
        visibleMask.setStrokeWidth(Settings.get().editEdgeStrokeWidth());
        visibleMask.setStroke(Settings.get().editEdgeColorNormal());
        visibleMask.setMouseTransparent(true);
        getChildren().add(visibleMask);

        startBall = createEndpointCircle(0, 0);
        getChildren().add(startBall);

        endBall = startBall;

        collisionMask = new Line();
        updateLinePosition(collisionMask);
        collisionMask.setStroke(Color.TRANSPARENT);
        collisionMask.setStrokeWidth(Settings.get().editEdgeCollisionMaskWidth());
        getChildren().add(collisionMask);

        initEventHandlers(start, end);
    }

    private Circle createEndpointCircle(double x, double y) {
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setStroke(Settings.get().editEdgeColorNormal());
        circle.setStroke(Settings.get().editEdgeColorNormal());
        circle.setMouseTransparent(true);
        return circle;
    }
    private void initEventHandlers(NodeData start, NodeData end) {
        start.positionChanged()
                .mergeWith(end.positionChanged())
                .subscribe(e -> {
                    updateLinePosition(visibleMask);
                    updateLinePosition(collisionMask);
                });
        collisionMask.setOnMouseEntered(e -> isMouseOver.setValue(true));
        collisionMask.setOnMouseExited(e -> isMouseOver.setValue(false));
        isMouseOver.changed()
                .mergeWith(highlightOnMouseOver.changed())
                .subscribe(e -> updateHighlightState());
        isMouseOver.changed()
                .mergeWith(enlargeOnMouseOver.changed())
                .subscribe(e -> {
                    double width = isMouseOver.value() && enlargeOnMouseOver.value() ?
                            Settings.get().editEdgeEnlargeRatio() *
                                    Settings.get().editEdgeStrokeWidth() :
                            1.0 * Settings.get().editEdgeStrokeWidth();
                    visibleMask.setStrokeWidth(width);
                });
    }

    public NodeData start() {
        return start;
    }
    public NodeData end() {
        return end;
    }
    public EndpointPair<NodeData> edge() {
        return EndpointPair.unordered(start, end);
    }
    public boolean highlightOnMouseOver() {
        return highlightOnMouseOver.value();
    }
    public void setHighlightOnMouseOver(boolean value) {
        highlightOnMouseOver.setValue(value);
    }
    public boolean enlargeOnMouseOver() {
        return enlargeOnMouseOver.value();
    }
    public void setEnlargeOnMouseOver(boolean value) {
        enlargeOnMouseOver.setValue(value);
    }

    private void updateHighlightState() {
        Color stroke = isMouseOver.value() && highlightOnMouseOver.value() ?
                Settings.get().editEdgeColorHighlight() :
                Settings.get().editEdgeColorNormal();
        visibleMask.setStroke(stroke);
    }

    private void updateLinePosition(Line line) {
        line.setStartX(start.getxCoordinate());
        line.setStartY(start.getyCoordinate());
        line.setEndX(end.getxCoordinate());
        line.setEndY(end.getyCoordinate());
    }
}
