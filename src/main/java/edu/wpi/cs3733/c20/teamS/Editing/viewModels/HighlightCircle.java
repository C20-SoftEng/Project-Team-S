package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import edu.wpi.cs3733.c20.teamS.utilities.rx.ReactiveProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Displays a circle that can be configured to highlight and enlarge on mouse-over.
 */
public class HighlightCircle extends Parent {
    private static final Object UNIT = new Object();

    private final Circle visibleMask;
    private final Circle collisionMask;
    private final ReactiveProperty<Boolean> isMouseOver = new ReactiveProperty<>(false);
    private final ReactiveProperty<Boolean> highlightOnMouseOver = new ReactiveProperty<>(true);
    private final ReactiveProperty<Color> highlightFillColor = new ReactiveProperty<>(Color.AQUA);
    private final ReactiveProperty<Color> normalFillColor = new ReactiveProperty<>(Color.BLUE);
    private final ReactiveProperty<Color> highlightStrokeColor = new ReactiveProperty<>(Color.AQUA);
    private final ReactiveProperty<Color> normalStrokeColor = new ReactiveProperty<>(Color.BLUE);

    private final ReactiveProperty<Boolean> enlargeOnMouseOver = new ReactiveProperty<>(true);
    private final ReactiveProperty<Double> normalRadius = new ReactiveProperty<>(12.0);
    private final ReactiveProperty<Double> highlightRadius = new ReactiveProperty<>(20.0);
    private final ReactiveProperty<Double> collisionRadius = new ReactiveProperty<>(20.0);

    
    public HighlightCircle(double normalRadius, double highlightRadius) {
        this();

        this.normalRadius.setValue(normalRadius);
        this.highlightRadius.setValue(highlightRadius);
        this.collisionRadius.setValue(highlightRadius);
    }
    public HighlightCircle() {
        visibleMask = new Circle();
        visibleMask.setMouseTransparent(true);
        getChildren().add(visibleMask);

        collisionMask = new Circle();
        collisionMask.setFill(Color.TRANSPARENT);
        collisionMask.setStroke(Color.TRANSPARENT);
        getChildren().add(collisionMask);

        collisionMask.setOnMouseEntered(e -> isMouseOver.setValue(true));
        collisionMask.setOnMouseExited(e -> isMouseOver.setValue(false));

        isMouseOver.changed().map(huh -> UNIT)
                .mergeWith(highlightOnMouseOver.changed())
                .mergeWith(highlightFillColor.changed())
                .mergeWith(normalFillColor.changed())
                .mergeWith(highlightStrokeColor.changed())
                .mergeWith(normalStrokeColor.changed())
                .subscribe(u -> updateHighlightState());

        isMouseOver.changed().map(huh -> UNIT)
                .mergeWith(enlargeOnMouseOver.changed())
                .mergeWith(highlightRadius.changed())
                .mergeWith(normalRadius.changed())
                .mergeWith(collisionRadius.changed())
                .subscribe(u -> updateVisibleRadius());

        updateVisibleRadius();
        updateHighlightState();
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

    public Color highlightFillColor() {
        return highlightFillColor.value();
    }
    public void setHighlightFillColor(Color value) {
        highlightFillColor.setValue(value);
    }
    public Color normalFillColor() {
        return normalFillColor.value();
    }
    public void setNormalFillColor(Color value) {
        normalFillColor.setValue(value);
    }
    public Color highlightStrokeColor() {
        return highlightStrokeColor.value();
    }
    public void setHighlightStrokeColor(Color value) {
        highlightStrokeColor.setValue(value);
    }
    public Color normalStrokeColor() {
        return normalStrokeColor.value();
    }
    public void setNormalStrokeColor(Color value) {
        normalStrokeColor.setValue(value);
    }

    public double normalRadius() {
        return normalRadius.value();
    }
    public void setNormalRadius(double value) {
        normalRadius.setValue(value);
    }
    public double highlightRadius() {
        return highlightRadius.value();
    }
    public void setHighlightRadius(double value) {
        highlightRadius.setValue(value);
    }
    public double collisionRadius() {
        return collisionRadius.value();
    }
    public void setCollisionRadius(double value) {
        collisionRadius.setValue(value);
    }

    private void updateHighlightState() {
        if (highlightOnMouseOver.value() && isMouseOver.value()) {
            visibleMask.setFill(highlightFillColor.value());
            visibleMask.setStroke(highlightStrokeColor.value());
        }
        else {
            visibleMask.setFill(normalFillColor.value());
            visibleMask.setStroke(normalStrokeColor.value());
        }
    }
    private void updateVisibleRadius() {
        double visibleRadius = enlargeOnMouseOver.value() && isMouseOver.value() ?
                highlightRadius.value() :
                normalRadius.value();
        visibleMask.setRadius(visibleRadius);
        collisionMask.setRadius(collisionRadius.value());
    }
}
