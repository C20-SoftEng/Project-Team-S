package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.utilities.numerics.Numerics;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class MapZoomer {
    private final ScrollPane scrollPane;
    private int zoomStage;
    private final int minZoomStage;
    private final int maxZoomStage;

    public MapZoomer(ScrollPane scrollPane) {
        this(scrollPane, -3, 3);
    }
    public MapZoomer(ScrollPane scrollPane, int minZoomStage, int maxZoomStage) {
        if (scrollPane == null) ThrowHelper.illegalNull("scrollPane");
        if (minZoomStage > maxZoomStage)
            throw new IllegalArgumentException("'minZoomStage' can't be greater than 'maxZoomStage'.");

        this.scrollPane = scrollPane;
        this.minZoomStage = minZoomStage;
        this.maxZoomStage = maxZoomStage;
        zoomStage = Numerics.clamp(0, minZoomStage, maxZoomStage);
    }

    public double zoomFactor() {
        //  Zoom factor is incremented in stages, just like stat-changes in Pokemon.
        //  Currently, the stages for Accuracy and Evasion are used, which are equal to 1/3 per stage, as opposed
        //  to the stages that are used for the other stats, which are equal to 1/2 per stage.
        double result = 1.0 + Math.abs(zoomStage) / 3.0;
        if (zoomStage < 0) {
            result = 1.0 / result;
        }

        Node content = scrollPane.getContent();
        double portWidth = scrollPane.getViewportBounds().getWidth();
        double portHeight = scrollPane.getViewportBounds().getHeight();
        double contentWidth = content.getBoundsInLocal().getWidth();
        double contentHeight = content.getBoundsInLocal().getHeight();

        return Math.max(result, Math.max(portWidth / contentWidth, portHeight / contentHeight));
    }

    private void updateImageSize() {
        Node content = scrollPane.getContent();
        content.setScaleX(zoomFactor());
        content.setScaleY(zoomFactor());
    }

    public boolean canZoomIn() {
        return zoomStage < maxZoomStage;
    }
    public void zoomIn() {
        if (canZoomIn()) {
            zoomStage++;
            updateImageSize();
        }
    }
    public boolean canZoomOut() {
        return zoomStage > minZoomStage;
    }
    public void zoomOut() {
        if (canZoomOut()) {
            zoomStage--;
            updateImageSize();
        }
    }

    public void zoomSet() {
        updateImageSize();
    }

    public int getZoomStage(){
        return this.zoomStage;
    }

}