package edu.wpi.cs3733.c20.teamS;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

public class MapZoomer {
    private final ImageView mapView;
    private ScrollPane scrollPane;
    private int zoomStage;
    private static final int minZoomStage = -2;
    private static final int maxZoomStage = 3;

    public MapZoomer(ImageView mapView, ScrollPane scrollPane) {
        this.mapView = mapView;
        this.scrollPane = scrollPane;
    }

    private double zoomFactor() {
        double result = 1.0 + 0.5 * Math.abs(zoomStage);
        if (zoomStage < 0) {
            return 1.0 / result;
        }

        return result;
    }

    private void updateImageSize() {
        this.mapView.setScaleX(zoomFactor());
        this.mapView.setScaleY(zoomFactor());
    }

    public void zoomIn() {
        if (zoomStage < maxZoomStage) {
            zoomStage++;
            updateImageSize();
        }
    }

    public void zoomOut() {
        if (zoomStage > minZoomStage) {
            zoomStage--;
            updateImageSize();
        }
        ;
    }

    private void updateBounds() {

        double rangeSize = zoomFactor();
        double minRange = 0;
        double maxRange = rangeSize;

    }
}