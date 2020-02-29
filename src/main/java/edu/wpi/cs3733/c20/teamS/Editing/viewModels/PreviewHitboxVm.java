package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import javafx.scene.Parent;
import javafx.scene.shape.Polygon;

import java.util.List;

public class PreviewHitboxVm extends Parent {
    private final Polygon previewPolygon;

    public PreviewHitboxVm(double startX, double startY) {
        previewPolygon = new Polygon();
        previewPolygon.getPoints().addAll(startX, startY, startX, startY);
        getChildren().add(previewPolygon);
    }

    public void setLastVertex(double x, double y) {
        List<Double> points = previewPolygon.getPoints();
        int size = points.size();
        points.set(size - 2, x);
        points.set(size - 1, y);
    }
    public void pushVertex(double x, double y) {
        previewPolygon.getPoints().addAll(x, y);
    }
}
