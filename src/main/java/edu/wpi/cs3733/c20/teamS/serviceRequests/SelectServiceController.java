package edu.wpi.cs3733.c20.teamS.serviceRequests;


import com.jfoenix.controls.JFXButton;
import edu.wpi.c20.teamU.FlowerRequest.RunFlowerRequest;
import edu.wpi.cs3733.c20.teamR.APILayer;
import edu.wpi.cs3733.c20.teamS.GiftRequest.GiftRequest;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamV.vVolesAPI.InterpreterRequester;
import foodRequest.FoodRequest;
import foodRequest.ServiceException;
import io.reactivex.rxjava3.subjects.PublishSubject;
import edu.wpi.cs3733.c20.teamS.app.serviceRequests.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    @FXML    private JFXButton foodButton;


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
//        InterpreterRequester requester = new InterpreterRequester();
//        try{
//            requester.run(0,0,0,0, "", "SLABS00201", "SDEPT00601");
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//        }
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

//        APILayer layer = new APILayer();
//        try{
//            layer.run(0,0,0,0,getClass().getResource("default.css").toString(),"SLABS00301", "SDEPT00504");
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
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
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //Date date = new Date();
    }

    @FXML void onFloristClicked(ActionEvent event){
        RunFlowerRequest request = new RunFlowerRequest();
        try{
            request.run(0,0,0,0,"", "SHALL00501", "SHALL00402");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
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

    @FXML void onGiftClicked(ActionEvent event){
        GiftRequest request = new GiftRequest();
        try{
            request.run(0,0,"","Room250");
        }
        catch(edu.wpi.cs3733.c20.teamS.Exceptions.ServiceException e){
            System.out.println(e.getMessage());
        }

        this.stage.close();
    }


    @FXML void onFoodClicked(ActionEvent event) throws ServiceException {
        FoodRequest foodRequest = new FoodRequest();
        foodRequest.run(0,0,500,500, null, "f","h");
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
