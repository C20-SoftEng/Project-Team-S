package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.ServiceTechServiceRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class ServiceTechRequestScreen {

    private final Stage stage;
    private final Scene scene;
    private final PublishSubject<DialogEvent<ServiceTechServiceRequest>> subject;

    public ServiceTechRequestScreen(Employee loggedIn){
        this.stage = new Stage();
        subject = PublishSubject.create();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/ServiceTechDialog.fxml"));
        loader.setControllerFactory(c -> {
            ServiceTechRequestController controller = new ServiceTechRequestController(loggedIn);
            controller.dialogCompleted().subscribe(
                    next -> {
                        ServiceTechServiceRequest request = next.value();
                        if(next.result() == DialogResult.OK){
                            //Do database
                            int dummyID = 0;
                            DatabaseController dbc = new DatabaseController();
                            ServiceData sd = new ServiceData(dummyID,"TECH", request.status().toString(),
                                    request.message(), "", loggedIn.id(), request.location());
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

    public static Observable<DialogEvent<ServiceTechServiceRequest>> showDialog(Employee employee) {
        if (employee == null) ThrowHelper.illegalNull("employee");

        ServiceTechRequestScreen screen = new ServiceTechRequestScreen(employee);
        screen.show();

        return screen.subject;
    }

    private void show() {
        stage.setScene(scene);
        stage.show();
    }
}
