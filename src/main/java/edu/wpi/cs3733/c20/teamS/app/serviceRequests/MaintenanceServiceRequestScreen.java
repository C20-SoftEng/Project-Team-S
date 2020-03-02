package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.BaseScreen;
import edu.wpi.cs3733.c20.teamS.Main;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.MaintenanceServiceRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MaintenanceServiceRequestScreen {
    private final Stage stage;
    private final Scene scene;
    private final PublishSubject<DialogEvent<MaintenanceServiceRequest>> subject;

    public MaintenanceServiceRequestScreen(Employee loggedIn){
        this.stage = new Stage();
        subject = PublishSubject.create();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/MaintenanceRequestDialog.fxml"));
        loader.setControllerFactory(c -> {
            MaintenanceServiceRequestController controller = new MaintenanceServiceRequestController(loggedIn);
            controller.dialogCompleted().subscribe(
                next -> {
                    if(next.result() == DialogResult.OK){
                        //Do Database
                        
                        MaintenanceServiceRequest mrequest = next.value();
                        DatabaseController db_controller = new DatabaseController();
                        String type = "MTNC";
                        String status = mrequest.status().toString();
                        String message = mrequest.message();
                        String data = "";
                        int employeeID = mrequest.assignee().id();
                        String location = mrequest.location();
                        int dummyID = 0;
                        ServiceData mrequestData = new ServiceData(type, status, message, data, employeeID, location);
                        System.out.println(mrequestData.toString());
                        db_controller.addServiceRequestData(mrequestData);

                        this.stage.close();
                    }
                    this.stage.close();
                }
            );
            return controller;
        });

        try{
            Parent root = loader.load();
            scene = new Scene(root);
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    public static Observable<DialogEvent<MaintenanceServiceRequest>> showDialog(Employee employee){
        if(employee == null) ThrowHelper.illegalNull("employee");

        MaintenanceServiceRequestScreen screen = new MaintenanceServiceRequestScreen(employee);
        screen.show();

        return screen.subject;
    }

    private void show(){
        this.stage.setScene(this.scene);
        Settings.openWindows.add(this.stage);
        BaseScreen.puggy.register(scene, Event.ANY);
        this.stage.show();
    }


}
