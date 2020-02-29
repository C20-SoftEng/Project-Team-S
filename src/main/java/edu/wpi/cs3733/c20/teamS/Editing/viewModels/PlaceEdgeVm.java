package edu.wpi.cs3733.c20.teamS.Editing.viewModels;

import edu.wpi.cs3733.c20.teamS.Settings;
import javafx.scene.Parent;
import javafx.scene.shape.Line;

/**
 * The preview edge that is displayed when placing an edge in the map editor.
 */
public class PlaceEdgeVm extends Parent {
    private final Line line;

    public PlaceEdgeVm(double startX, double startY) {
        setTranslateX(startX);
        setTranslateY(startY);

        line = new Line();
        line.setStartX(0);
        line.setStartY(0);
        line.setEndX(0);
        line.setEndY(0);
        line.setStrokeWidth(Settings.get().editEdgeStrokeWidth());
        line.setStroke(Settings.get().editEdgeColorNormal());
        line.setMouseTransparent(true);
        getChildren().add(line);
    }

    public void setEnd(double endX, double endY) {
        line.setEndX(endX - getTranslateX());
        line.setEndY(endY - getTranslateY());
    }
}
