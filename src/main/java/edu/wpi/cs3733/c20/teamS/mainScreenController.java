package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainScreenController implements Initializable {
    int current_floor = 2;
    private MapZoomer zoomer;
    Image floor1 = new Image("images/Floors/HospitalFloor1.png");
    Image floor2 = new Image("images/Floors/HospitalFloor2.png");
    Image floor3 = new Image("images/Floors/HospitalFloor3.png");
    Image floor4 = new Image("images/Floors/HospitalFloor4.png");
    Image floor5 = new Image("images/Floors/HospitalFloor5.png");

    @FXML
    private ImageView mapImage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXButton floorButton1;
    @FXML
    private JFXButton floorButton2;
    @FXML
    private JFXButton floorButton3;
    @FXML
    private JFXButton floorButton4;
    @FXML
    private JFXButton floorButton5;
    @FXML
    private JFXButton downButton;
    @FXML
    private JFXButton upButton;

    @FXML
    void onFloorClicked1(ActionEvent event) {
        set1();
    }

    @FXML
    void onFloorClicked2(ActionEvent event) {
        set2();
    }

    @FXML
    void onFloorClicked3(ActionEvent event) {
        set3();
    }

    @FXML
    void onFloorClicked4(ActionEvent event) {
        set4();
    }

    @FXML
    void onFloorClicked5(ActionEvent event) {
        set5();
    }

    @FXML
    void onUpClicked(ActionEvent event) {
        current_floor += 1;
        if (current_floor == 1) {
            set1();
        } else if (current_floor == 2) {
            set2();
        } else if (current_floor == 3) {
            set3();
        } else if (current_floor == 4) {
            set4();
        } else if (current_floor == 5) {
            set5();
        }
    }

    @FXML
    void onDownClicked(ActionEvent event) {
        current_floor -= 1;
        if (current_floor == 1){
            set1();
        } else if (current_floor == 2) {
            set2();
        } else if (current_floor == 3) {
            set3();
        } else if (current_floor == 4) {
            set4();
        } else if (current_floor == 5) {
            set5();
        }
    }

    void set1() {
        floorButton1.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        upButton.setDisable(false);
        downButton.setDisable(true);
        mapImage.setImage(floor1);
        current_floor = 1;
    }

    void set2() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        mapImage.setImage(floor2);
        upButton.setDisable(false);
        downButton.setDisable(false);
        current_floor = 2;
    }

    void set3() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        mapImage.setImage(floor3);
        current_floor = 3;
        upButton.setDisable(false);
        downButton.setDisable(false);
    }

    void set4() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        floorButton5.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        mapImage.setImage(floor4);
        current_floor = 4;
        upButton.setDisable(false);
        downButton.setDisable(false);
    }

    void set5() {
        floorButton1.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton2.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton3.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton4.setStyle("-fx-background-color: #ffffff; -fx-font: 22 System;");
        floorButton5.setStyle("-fx-background-color: #f6bd38; -fx-font: 32 System;");
        mapImage.setImage(floor5);
        current_floor = 5;
        upButton.setDisable(true);
        downButton.setDisable(false);
    }

    @FXML
    void onHelpClicked(ActionEvent event) {
    }

    @FXML
    void onStaffClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/loginScreen.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Login to the System");
            window.setScene(new Scene(root1));
            window.setResizable(false);
            window.show();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }

    @FXML
    void onZoomInClicked(ActionEvent event) {
        this.zoomer.zoomIn();
    }

    @FXML
    void onZoomOutClicked(ActionEvent event) {
        Node content = scrollPane.getContent();
        this.zoomer.zoomOut();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomer = new MapZoomer(mapImage, scrollPane);
    }
}

