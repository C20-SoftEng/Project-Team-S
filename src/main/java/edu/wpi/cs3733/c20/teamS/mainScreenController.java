package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;

import java.awt.*;

public class mainScreenController {
    final double SCALE_DELTA = 1.1;
    public double SCALE_TOTAL = 1;

        @FXML
        private JFXButton upButton;

        @FXML
        private JFXButton floorButton5;

        @FXML
        private JFXButton floorButton4;

        @FXML
        private JFXButton floorButton3;

        @FXML
        private JFXButton floorButton2;

        @FXML
        private JFXButton floorButton1;

        @FXML
        private JFXButton downButton;

        @FXML
        private JFXButton staffButton;

        @FXML
        private JFXButton staffButton1;

        @FXML
        private JFXButton helpButton;

        @FXML
        private ScrollPane mapPane;

        @FXML
        private ImageView mapImage;

        @FXML
        private JFXButton zoomInButton;

        @FXML
        private JFXButton zoomOutButton;

        @FXML
        void floorClicked1(ActionEvent event) {

        }

        @FXML
        void floorClicked2(ActionEvent event) {

        }

        @FXML
        void floorClicked3(ActionEvent event) {

        }

        @FXML
        void onDownClicked(ActionEvent event) {

        }

        @FXML
        void onFloorClicked4(ActionEvent event) {

        }

        @FXML
        void onFloorClicked5(ActionEvent event) {

        }
    public mainScreenController() {
    }

    @FXML
    private ImageView mapImage;

        @FXML
        void onHelpClicked(ActionEvent event) {

        }

        @FXML
        void onUpClicked(ActionEvent event) {

        }

    @FXML
    void onStaffClicked(ActionEvent event) {

    }

    @FXML
    void onZoomInClicked(ActionEvent event){
        double current_width = this.mapImage.getFitWidth();
        double current_height = this.mapImage.getFitHeight();
        //this.mapImage.setPreserveRatio(true);
        this.mapImage.setFitWidth(1.2 * current_width);
        this.mapImage.setFitHeight(1.2 * current_height);
    }

    @FXML
    void onZoomOutClicked(ActionEvent event){
        double current_width = this.mapImage.getFitWidth();
        double current_height = this.mapImage.getFitHeight();
        //this.mapImage.setPreserveRatio(true);

        //Scales the map to zoom out.
        this.mapImage.addEventFilter(ScrollEvent.ANY, e-> {
                    e.consume();
                    if (e.getDeltaY() == 0) {
                        return;
                    }
        double scaleFactor = (e.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;
        if (scaleFactor * SCALE_TOTAL >= 1) {
            this.mapImage.setScaleX(mapImage.getScaleX() * scaleFactor);
            this.mapImage.setScaleY(mapImage.getScaleY() * scaleFactor);
        }});

     //   this.mapImage.setFitWidth((1/1.2) * current_width);
      //  this.mapImage.setFitHeight((1/1.2) * current_height);
    }

}
