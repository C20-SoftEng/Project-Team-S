package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideServiceRequest;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class SelectServiceController {
    private Stage stage;

    private final PublishSubject<DialogEvent<RideServiceRequest>> dialogCompleted_ = PublishSubject.create();

    @FXML    private JFXButton rideButton;
    @FXML    private JFXButton interpreterButton;
    @FXML    private JFXButton floristButton;
    @FXML    private JFXButton janitorButton;
    @FXML    private JFXButton maintenanceButton;
    @FXML    private JFXButton medicineButton;
    @FXML    private JFXButton securityButton;
    @FXML    private JFXButton lastRitesButton;
    @FXML    private JFXButton giftButton;
    @FXML    private JFXButton laundryButton;
    @FXML    private JFXButton serviceTechService;
    @FXML    private JFXButton dogButton;

    @FXML void onDrugClick(ActionEvent event){
        Stage drugStage = new Stage();
        drugStage.initModality(Modality.WINDOW_MODAL);

    }

    @FXML void onDogClick(ActionEvent event){
        Stage dogStage = new Stage();
        dogStage.initModality(Modality.WINDOW_MODAL);

        ImageView iv = new ImageView();
        iv.setImage(new Image(this.getClass().getResource("/images/PugLickingScreen.gif").toExternalForm()));
        AnchorPane root = new AnchorPane(iv);

        Scene scene = new Scene(root, 300, 290);

        dogStage.setScene(scene);
        dogStage.show();

        this.stage.close();
    }

    /**
     * passes a stage so that the window can close from opening new dialog
     * @param stage
     */
    public SelectServiceController(Stage stage){
        this.stage = stage;
    }

}
