package edu.wpi.cs3733.c20.teamS.emergency;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.MainToLoginScreen;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EmergencyController {

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
    JFXButton onBackClicked;

    @FXML
    public void onBackClicked(ActionEvent event) {
        MainToLoginScreen screen = new MainToLoginScreen();

    }
}
