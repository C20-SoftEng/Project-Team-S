package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import edu.wpi.cs3733.c20.teamS.utilities.Numerics;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
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
        zoomStage = Numerics.clamp(-2, minZoomStage, maxZoomStage);
    }

    public double zoomFactor() {
        //  Zoom factor is incremented in stages, just like stat-changes in Pokemon.
        double result = 1.0 + 0.5 * Math.abs(zoomStage);
        if (zoomStage < 0) {
            return 1.0 / result;
        }

        return result;
    }

    private void updateImageSize() {
        scrollPane.getContent().setScaleX(zoomFactor());
        scrollPane.getContent().setScaleY(zoomFactor());
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