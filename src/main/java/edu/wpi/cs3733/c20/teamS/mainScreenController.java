package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class mainScreenController implements Initializable {
    final double SCALE_DELTA = 1.1;
    public double SCALE_TOTAL = 1;
    private MapZoomer zoomer;

    /*public mainScreenController() {

    }
*/

    @FXML private JFXButton upButton;

        @FXML private JFXButton floorButton5;

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

        //@FXML
        //private ScrollPane mapPane;

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

        this.zoomer.zoomIn();

    }

    @FXML void onZoomOutClicked(ActionEvent event){
        this.zoomer.zoomOut();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomer = new MapZoomer(mapImage);
    }
}
