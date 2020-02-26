package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.utilities.Tweetbox;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainStartScreenController {

    @FXML
    AnchorPane startScreen;

    @FXML
    VBox weatherBox;

    @FXML
    VBox timeBox;

    public void showTwitter() {
        Tweetbox tb = new Tweetbox();
        weatherBox.getChildren().add(tb);
    }
}
