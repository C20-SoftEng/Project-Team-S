package edu.wpi.cs3733.c20.teamS.emergency;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.MainToLoginScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmergencyController implements Initializable{

    @FXML private ImageView mapDisplay;
    @FXML private ScrollPane mapScroll;
    @FXML private Button exitEmergencyMode;
    @FXML private ImageView alertScrolling;
    private Stage stage;

    /**
     * Creates a Controller for the Emergency Screen
     * @param stage The stage to replace
     */
    public EmergencyController(Stage stage){
        if (stage == null) ThrowHelper.illegalNull("stage");
        this.stage = stage;
    }

    @FXML
    Button onBackClicked;

    @FXML
    public void onBackClicked(ActionEvent event) {
        MainToLoginScreen screen = new MainToLoginScreen();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String path = "/images/Floors/emergency/Emergency" + Settings.get().getKioskFloor()+".png";
        mapDisplay.setImage(new Image(String.valueOf(getClass().getResource(path))));
    }
}
