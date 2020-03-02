package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.BaseScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.LastRitesRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public final class LastRitesRequestScreen {


    private final Stage stage;
    private final Scene scene;
    private final PublishSubject<DialogEvent<LastRitesRequest>> subject;

    public LastRitesRequestScreen(Employee loggedIn){
        this.stage = new Stage();
        subject = PublishSubject.create();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/LastRitesDialog.fxml"));
        loader.setControllerFactory(c -> {
            LastRitesRequestController controller = new LastRitesRequestController(loggedIn);
            controller.dialogCompleted().subscribe(
                    next -> {
                        if(next.result() == DialogResult.OK){
                            DatabaseController dbc = new DatabaseController();
                            String serviceType = "LSRT";
                            String status = "Incomplete";
                            String message = next.value().message();
                            String data = "";
                            int assignedEmployeeID = next.value().assignee().id();
                            String serviceNode = next.value().location();
                            int dummyID = 0;

                            ServiceData sd = new ServiceData(serviceType,status,message,data,assignedEmployeeID,serviceNode);
                            dbc.addServiceRequestData(sd);
                        }
                        this.stage.close();
                    }
            );
            return controller;
        });

        try {
            Parent root = loader.load();
            scene = new Scene(root);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Observable<DialogEvent<LastRitesRequest>> showDialog(Employee employee) {
        if (employee == null) ThrowHelper.illegalNull("employee");

        LastRitesRequestScreen screen = new LastRitesRequestScreen(employee);
        screen.show();

        return screen.subject;
    }

    private void show() {
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        Settings.openWindows.add(this.stage);
        BaseScreen.puggy.register(scene, Event.ANY);
        stage.show();
    }


}
