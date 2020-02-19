package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.LaundryServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public final class LaundryRequestScreen {

    private final Stage stage;
    private final Scene scene;
    private final PublishSubject<DialogEvent<LaundryServiceRequest>> subject;

    public LaundryRequestScreen(Employee loggedIn){
        this.stage = new Stage();
        subject = PublishSubject.create();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/LaundryServiceDialog.fxml"));
        loader.setControllerFactory(c -> {
            LaundryRequestController controller = new LaundryRequestController(loggedIn);
            controller.dialogCompleted().subscribe(
                    next -> {
                        if(next.result() == DialogResult.OK){
                            DatabaseController dbc = new DatabaseController();
                            String serviceType = "LNDR";
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

    public static Observable<DialogEvent<LaundryServiceRequest>> showDialog(Employee employee) {
        if (employee == null) ThrowHelper.illegalNull("employee");

        LaundryRequestScreen screen = new LaundryRequestScreen(employee);
        screen.show();

        return screen.subject;
    }

    private void show() {
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
