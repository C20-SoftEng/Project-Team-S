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

public class employeeScreenController implements Initializable {
    int current_floor;
    String newFloor;
    private MapZoomer zoomer;
    Image floor1 = new Image("images/Floors/HospitalFloor1.png");
    Image floor2 = new Image("images/Floors/HospitalFloor2.png");
    Image floor3 = new Image("images/Floors/HospitalFloor3.png");
    Image floor4 = new Image("images/Floors/HospitalFloor4.png");
    Image floor5 = new Image("images/Floors/HospitalFloor5.png");

    @FXML private ImageView mapImage;

    @FXML private ScrollPane scrollPane;
    @FXML private JFXButton floorButton1;

    @FXML void onFloorClicked1(ActionEvent event) {
        mapImage.setImage(floor1);
        //old floorButton size 22
        //old floor button color #FFFFFF
        current_floor = 1;
        floorButton1.setStyle("-fx-background-color: #f6bd38");
        //#f6bd38 - yellow button color
        //font 32
        //grey out floor down
    }

    @FXML void onFloorClicked2(ActionEvent event) {
        mapImage.setImage(floor2);
        current_floor = 2;
    }

    @FXML void onFloorClicked3(ActionEvent event) {
        mapImage.setImage(floor3);
        current_floor = 3;
    }

    @FXML void onFloorClicked4(ActionEvent event) {
        mapImage.setImage(floor4);
        current_floor = 4;
        System.out.println("images/Floors/HospitalFloor" + Integer.toString(current_floor) + ".png");
    }

    @FXML void onFloorClicked5(ActionEvent event) {
        mapImage.setImage(floor5);
        current_floor = 5;
        mapImage.setImage(new Image(newFloor));
        //grey out
    }
     //#f6bd38 - yellow button color
    //font 32
    //font 22
    //floorButton3

    @FXML void onUpClicked(ActionEvent event) {
        if(current_floor != 5){
            current_floor += 1;
            newFloor = "images/Floors/HospitalFloor" + Integer.toString(current_floor) + ".png";
            mapImage.setImage(new Image(newFloor));
        }
        else{
            //grey out
        }

    }

    @FXML void onDownClicked(ActionEvent event) {
        if(current_floor != 1){
            current_floor -= 1;
            newFloor = "images/Floors/HospitalFloor" + Integer.toString(current_floor) + ".png";
            mapImage.setImage(new Image(newFloor));
        }
        else{
            //grey out
        }
    }

    @FXML void onHelpClicked(ActionEvent event) {
    }

    @FXML void onLogOut(ActionEvent event){

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