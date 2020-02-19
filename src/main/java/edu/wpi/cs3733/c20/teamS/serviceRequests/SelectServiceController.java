package edu.wpi.cs3733.c20.teamS.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.app.serviceRequests.DrugRequestScreen;
import edu.wpi.cs3733.c20.teamS.app.serviceRequests.SecurityServiceScreen;
import edu.wpi.cs3733.c20.teamS.app.serviceRequests.ServiceTechRequestScreen;
import edu.wpi.cs3733.c20.teamS.app.serviceRequests.LaundryRequestScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectServiceController {
    private Stage stage;
    private Employee loggedIn;

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

    @FXML void onDrugClicked(ActionEvent event){
        Stage drugStage = new Stage();
        drugStage.initModality(Modality.WINDOW_MODAL);

        DrugRequestScreen.showDialog(loggedIn).subscribe();
        this.stage.close();
    }

    @FXML void onSecurityClicked(ActionEvent event) {
        Stage security = new Stage();
        security.initModality(Modality.WINDOW_MODAL);

        SecurityServiceScreen.showDialog(loggedIn).subscribe();
        this.stage.close();
    }
    @FXML void onServiceTechClicked(ActionEvent event){
        Stage serviceTechStage = new Stage();
        serviceTechStage.initModality(Modality.WINDOW_MODAL);

        ServiceTechRequestScreen.showDialog(loggedIn).subscribe();
        this.stage.close();
    }

    @FXML void onLaundryClicked(ActionEvent event){
        Stage laundryStage = new Stage();
        laundryStage.initModality(Modality.WINDOW_MODAL);

        LaundryRequestScreen.showDialog(loggedIn).subscribe();

        this.stage.close();
    }

    @FXML void onDogClicked(ActionEvent event){
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
     * @param employee the currently logged in employee
     */
    public SelectServiceController(Stage stage, Employee employee){
        this.stage = stage;
        this.loggedIn = employee;
    }

}
