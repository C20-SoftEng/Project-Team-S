package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.JanitorServiceRequest;
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

public final class JanitorRequestScreen {

    private final Stage stage;
    private final Scene scene;
    private final PublishSubject<DialogEvent<JanitorServiceRequest>> subject;

    public JanitorRequestScreen(Employee loggedIn){
        this.stage = new Stage();
        subject = PublishSubject.create();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/JanitorServiceDialog.fxml"));
        loader.setControllerFactory(c -> {
            JanitorRequestController controller = new JanitorRequestController(loggedIn);
            controller.dialogCompleted().subscribe(
                    next -> {
                        if(next.result() == DialogResult.OK){
                            //Do database
                            JanitorServiceRequest janitorServiceRequest = next.value();
                            DatabaseController db = new DatabaseController();
                            //db.addServiceRequestData(new ServiceData(0, "JANT", janitorServiceRequest.id()));
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

    public static Observable<DialogEvent<JanitorServiceRequest>> showDialog(Employee employee) {
        if (employee == null) ThrowHelper.illegalNull("employee");

        JanitorRequestScreen screen = new JanitorRequestScreen(employee);
        screen.show();

        return screen.subject;
    }

    private void show() {
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
