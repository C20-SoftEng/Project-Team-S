package edu.wpi.cs3733.c20.teamS.serviceRequests;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import io.reactivex.rxjava3.subjects.PublishSubject;
import edu.wpi.cs3733.c20.teamS.app.serviceRequests.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;

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
    @FXML void onInterpreterClicked(ActionEvent event){
        Stage interpreterStage = new Stage();
        interpreterStage.initModality(Modality.WINDOW_MODAL);

        InterpreterRequestScreen.showDialog(loggedIn).subscribe();
        this.stage.close();
    }

    @FXML void onMaintenanceClicked(ActionEvent event){
        Stage maintenanceStage = new Stage();
        maintenanceStage.initModality(Modality.WINDOW_MODAL);

        MaintenanceServiceRequestScreen.showDialog(loggedIn).subscribe();
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

    @FXML void onLastRitesClicked(ActionEvent event){
        Stage lastRitesStage = new Stage();
        lastRitesStage.initModality(Modality.WINDOW_MODAL);

        LastRitesRequestScreen.showDialog(loggedIn).subscribe();

        this.stage.close();
    }

    @FXML void onFloristClicked(ActionEvent event){
        Stage floristStage = new Stage();
        floristStage.initModality(Modality.WINDOW_MODAL);

        FloristRequestScreen.showDialog(loggedIn).subscribe();
        this.stage.close();
    }

    @FXML void onRideClicked(ActionEvent event){
        Stage rideStage = new Stage();
        rideStage.initModality(Modality.WINDOW_MODAL);

        RideRequestScreen.showDialog(rideStage, loggedIn).subscribe();
        this.stage.close();
    }

    @FXML void onJanitorClicked(ActionEvent event){
        Stage janitorStage = new Stage();
        janitorStage.initModality(Modality.WINDOW_MODAL);

        JanitorRequestScreen.showDialog(loggedIn).subscribe();
        this.stage.close();
    }


    @FXML void onDogClicked(ActionEvent event){
        Stage dogStage = new Stage();
        dogStage.initModality(Modality.WINDOW_MODAL);

        ImageView iv = new ImageView();
        iv.setImage(new Image(this.getClass().getResource("/images/PugLickingScreen.gif").toExternalForm()));
        AnchorPane root = new AnchorPane(iv);

        Scene scene = new Scene(root, 325, 250);

        dogStage.setScene(scene);
        dogStage.setResizable(false);
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
