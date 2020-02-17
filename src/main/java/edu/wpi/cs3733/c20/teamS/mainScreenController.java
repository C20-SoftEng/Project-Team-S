package edu.wpi.cs3733.c20.teamS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;
public class mainScreenController implements Initializable {
    private MapZoomer zoomer;
    Image floor1 = new Image("images/Floors/HospitalFloor1.png");
    Image floor2 = new Image("images/Floors/HospitalFloor2.png");
    Image floor3 = new Image("images/Floors/HospitalFloor3.png");
    Image floor4 = new Image("images/Floors/HospitalFloor4.png");
    Image floor5 = new Image("images/Floors/HospitalFloor5.png");

    @FXML private ImageView mapImage;

    @FXML private ScrollPane scrollPane;

    @FXML void onFloorClicked1(ActionEvent event) {
        mapImage.setImage(floor1);
    }

    @FXML void onFloorClicked2(ActionEvent event) {
        mapImage.setImage(floor2);
    }

    @FXML void onFloorClicked3(ActionEvent event) {
        mapImage.setImage(floor3);
    }

    @FXML void onFloorClicked4(ActionEvent event) {
        mapImage.setImage(floor4);
    }

    @FXML void onFloorClicked5(ActionEvent event) {
        mapImage.setImage(floor5);
    }
     //#f6bd38 - yellow button color

    @FXML void onHelpClicked(ActionEvent event) {
    }

    @FXML void onStaffClicked(ActionEvent event) {
    }

    @FXML void onZoomInClicked(ActionEvent event) {
        this.zoomer.zoomIn();
    }

    @FXML void onZoomOutClicked(ActionEvent event) {
        Node content = scrollPane.getContent();
        this.zoomer.zoomOut();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomer = new MapZoomer(mapImage, scrollPane);
    }
}