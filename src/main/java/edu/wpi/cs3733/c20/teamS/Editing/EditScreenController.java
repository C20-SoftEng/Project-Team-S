package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.c20.teamS.MapZoomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class EditScreenController implements Initializable {
    @FXML private JFXRadioButton addNode;
    @FXML private ScrollPane scrollPane;
    public EditScreenController() {}
    public JFXRadioButton getAddNode() {return addNode;}
    public ScrollPane getScrollPane() {return scrollPane;}

    int current_floor = 2;
    String newFloor;
    private MapZoomer zoomer;
    Image floor1 = new Image("images/Floors/HospitalFloor1.png");
    Image floor2 = new Image("images/Floors/HospitalFloor2.png");
    Image floor3 = new Image("images/Floors/HospitalFloor3.png");
    Image floor4 = new Image("images/Floors/HospitalFloor4.png");
    Image floor5 = new Image("images/Floors/HospitalFloor5.png");
    ImageView mapImage = new ImageView(floor2);

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
