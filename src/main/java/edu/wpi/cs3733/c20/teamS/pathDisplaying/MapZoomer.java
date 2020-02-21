package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

public class MapZoomer {
    private final ImageView mapView;
    private final ScrollPane scrollPane;
    private int zoomStage;
    private static final int minZoomStage = -2;
    private static final int maxZoomStage = 3;

    public MapZoomer(ImageView mapView, ScrollPane scrollPane) {
        this.mapView = mapView;
        this.scrollPane = scrollPane;
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