package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class mainScreenController {

    @FXML
    private JFXButton staffButton;

    @FXML
    private JFXButton staffButton1;

    @FXML
    private JFXButton helpButton;


    @FXML
    private JFXButton coffeeButton;

    @FXML
    private JFXButton vendingButton;

    @FXML
    private JFXButton foodButton;

    @FXML
    private JFXButton informationButton;

    @FXML
    private JFXButton elevatorButton;

    @FXML
    private JFXButton zoomInButton;

    @FXML
    private JFXButton zoomOutButton;

    @FXML
    private ImageView mapImage;

    @FXML
    void onHelpClicked(ActionEvent event) {

    }

    @FXML
    void onStaffClicked(ActionEvent event) {

    }

    @FXML
    void onZoomInClicked(ActionEvent event){
        double current_width = this.mapImage.getFitWidth();
        double current_height = this.mapImage.getFitHeight();
        this.mapImage.setPreserveRatio(true);
        this.mapImage.setFitWidth(1.2 * current_width);
        this.mapImage.setFitHeight(1.2 * current_height);
    }

    @FXML
    void onZoomOutClicked(ActionEvent event){
        double current_width = this. mapImage.getFitWidth();
        double current_height = this. mapImage.getFitHeight();
        this.mapImage.setPreserveRatio(true);
        this.mapImage.setFitWidth((1/1.2) * current_width);
        this.mapImage.setFitHeight((1/1.2) * current_height);
    }

}
