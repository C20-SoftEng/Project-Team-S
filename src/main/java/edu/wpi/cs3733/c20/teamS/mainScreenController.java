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

@FXML private void onZoomInClicked(ActionEvent event){

    zoomer.zoomIn();

}
@FXML private void onZoomOutClicked(ActionEvent event){

    zoomer.zoomOut();
}

@Override
public void initialize(URL location, ResourceBundle resources) {
    zoomer = new MapZoomer(mapImage, scrollPane);
}
}
