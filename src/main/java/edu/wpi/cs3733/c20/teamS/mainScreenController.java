package edu.wpi.cs3733.c20.teamS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class mainScreenController implements Initializable {

    private MapZoomer zoomer;
    @FXML private ImageView mapImage;
    @FXML private ScrollPane scrollPane;

        @FXML
        private ImageView mapImage;

        @FXML private ScrollPane mapPane;

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

    @FXML private void onZoomInClicked(ActionEvent event){

        this.zoomer.zoomIn();

    }
    @FXML private void onZoomOutClicked(ActionEvent event){

        zoomer.zoomOut();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomer = new MapZoomer(mapImage, scrollPane);
    }

}
