package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.BaseScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class DrugRequestScreen {

    private final Stage stage;
    private final Scene scene;
    private final PublishSubject<DialogEvent<DrugServiceRequest>> subject;

    public DrugRequestScreen(Employee loggedIn){
        this.stage = new Stage();
        subject = PublishSubject.create();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/DrugServiceDialog.fxml"));
        loader.setControllerFactory(c -> {
            DrugRequestController controller = new DrugRequestController(loggedIn);
            controller.dialogCompleted().subscribe(
                    next -> {
                            if(next.result() == DialogResult.OK){
                                DatabaseController dbc = new DatabaseController();
                                String serviceType = "DRUG";
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

    public static Observable<DialogEvent<DrugServiceRequest>> showDialog(Employee employee) {
        if (employee == null) ThrowHelper.illegalNull("employee");

        DrugRequestScreen screen = new DrugRequestScreen(employee);
        screen.show();

        return screen.subject;
    }

    private void show() {
        stage.setScene(scene);

        Settings.openWindows.add(this.stage);
        BaseScreen.puggy.register(scene, Event.ANY);

        stage.show();
    }
}
