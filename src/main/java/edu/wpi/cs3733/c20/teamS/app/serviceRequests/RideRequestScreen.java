package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideServiceRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class RideRequestScreen {
    private final Stage stage;
    private final PublishSubject<DialogEvent<RideServiceRequest>> subject;
    private final Scene scene;
    private Employee loggedIn;

    private RideRequestScreen(Stage stage, Employee logged) {
        this.stage = stage;
        subject = PublishSubject.create();
        this.loggedIn = logged;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/RideRequestDialog.fxml"));
        loader.setControllerFactory(c -> {
            RideRequestUIController controller = new RideRequestUIController(loggedIn);
            controller.dialogCompleted().subscribe(next -> {
                        if(next.result() == DialogResult.OK){
                            DatabaseController dbc = new DatabaseController();
                            String serviceType = "RIDE";
                            String status = "Incomplete";
                            String message = next.value().message();
                            String data = "";
                            int assignedEmployeeID = next.value().assignee().id();
                            String serviceNode = next.value().location();
                            int dummyID = 0;

                            ServiceData sd = new ServiceData(dummyID,serviceType,status,message,data,assignedEmployeeID,serviceNode);
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

    private void show() {
        stage.setScene(scene);
        stage.show();
    }

    public static Observable<DialogEvent<RideServiceRequest>> showDialog(Stage stage, Employee loggedIn) {
        if (stage == null) ThrowHelper.illegalNull("stage");

        RideRequestScreen screen = new RideRequestScreen(stage, loggedIn);
        screen.show();

        return screen.subject;
    }
}
