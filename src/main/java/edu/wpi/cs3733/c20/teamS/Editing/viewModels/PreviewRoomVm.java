package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import edu.wpi.cs3733.c20.teamS.Settings;
import javafx.scene.Parent;
import javafx.scene.shape.Polygon;

import java.util.List;

public class PreviewRoomVm extends Parent {
    private final Polygon previewPolygon;

    public PreviewRoomVm(double startX, double startY) {
        setTranslateX(startX);
        setTranslateY(startY);
        previewPolygon = new Polygon();
        previewPolygon.getPoints().addAll(0.0, 0.0, 0.0, 0.0);
        previewPolygon.setFill(Settings.get().editRoomColorNormal());
        getChildren().add(previewPolygon);
    }

    public void setLastVertex(double x, double y) {
        List<Double> points = previewPolygon.getPoints();
        int size = points.size();
        points.set(size - 2, x - getTranslateX());
        points.set(size - 1, y - getTranslateY());
    }
    public void pushVertex(double x, double y) {
        previewPolygon.getPoints().addAll(x - getTranslateX(), y - getTranslateY());
    }
}
