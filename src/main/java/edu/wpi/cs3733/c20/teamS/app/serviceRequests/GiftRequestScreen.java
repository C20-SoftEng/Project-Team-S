package edu.wpi.cs3733.c20.teamS.app.serviceRequests;
import edu.wpi.cs3733.c20.teamS.BaseScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.app.serviceRequests.DrugRequestController;
import edu.wpi.cs3733.c20.teamS.app.serviceRequests.GiftRequestController;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.GiftServiceRequest;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class GiftRequestScreen {

    private final Stage stage;
    private final Scene scene;
    private final PublishSubject<DialogEvent<GiftServiceRequest>> subject;

    public GiftRequestScreen(Employee loggedIn){
        this.stage = new Stage();
        subject = PublishSubject.create();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/GiftDialogScreen.fxml"));
        loader.setControllerFactory(c -> {
            GiftRequestController controller = new GiftRequestController(loggedIn);
            controller.dialogCompleted().subscribe(
                    next -> {
                        if(next.result() == DialogResult.OK){
                            //Do database
                            DatabaseController dbc = new DatabaseController();
                            dbc.addServiceRequestData(new ServiceData("GIFT","INCOMPLETE",next.value().gift(),"",next.value().assignee().id(),next.value().location()));
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

    public static Observable<DialogEvent<GiftServiceRequest>> showDialog(Employee employee) {
        if (employee == null) ThrowHelper.illegalNull("employee");

        GiftRequestScreen screen = new GiftRequestScreen(employee);
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