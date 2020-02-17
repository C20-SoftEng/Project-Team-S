package edu.wpi.cs3733.c20.teamS;
import com.jfoenix.controls.JFXButton;
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
    final double SCALE_DELTA = 1.1;
    public double SCALE_TOTAL = 1;
    private MapZoomer zoomer;
    Image floor1 = new Image("images/Floors/hospital_1st_floor.png");
    Image floor2 = new Image("images/Floors/hospital_2nd_floor.png");
    Image floor3 = new Image("images/Floors/hospital_3rd_floor.png");
    Image floor4 = new Image("images/Floors/hospital_4th_floor.png");
    Image floor5 = new Image("images/Floors/hospital_5th_floor.png");
    @FXML
    private ImageView mapImage;
    @FXML
    private ScrollPane mapPane;
    @FXML
    void floorClicked1(ActionEvent event) {
        mapImage.setImage(floor1);
    }
    @FXML
    void floorClicked2(ActionEvent event) {
        mapImage.setImage(floor2);
    }
    @FXML
    void floorClicked3(ActionEvent event) {
        mapImage.setImage(floor3);
    }
    @FXML
    void onFloorClicked4(ActionEvent event) {
        mapImage.setImage(floor4);
    }
    @FXML
    void onFloorClicked5(ActionEvent event) {
        mapImage.setImage(floor5);
    }
    @FXML
    void onUpClicked(ActionEvent event) {
        mapImage.setImage(new Image("images/Floors/hospital_4th_floor.png"));
    }
    @FXML
    void onDownClicked(ActionEvent event) {
        mapImage.setImage(new Image("images/Floors/hospital_4th_floor.png"));
    }
    @FXML
    void onHelpClicked(ActionEvent event) {
    }
    @FXML
    void onStaffClicked(ActionEvent event) {
    }
    @FXML
    void onZoomInClicked(ActionEvent event) {
        this.zoomer.zoomIn();
    }
    @FXML
    void onZoomOutClicked(ActionEvent event) {
        Node content = mapPane.getContent();
        this.zoomer.zoomOut();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScrollPane scrollPane;
        zoomer = new MapZoomer(mapImage, scrollPane);
    }
}