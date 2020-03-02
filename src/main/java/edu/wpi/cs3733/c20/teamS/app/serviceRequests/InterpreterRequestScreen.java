package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.BaseScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.ServiceData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.InterpreterServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InterpreterRequestScreen {

    private final Stage stage;
    private final Scene scene;
    private final PublishSubject<DialogEvent<InterpreterServiceRequest>> subject;
    private Employee loggedIn;

    public InterpreterRequestScreen(Employee loggedIn){
        this.stage = new Stage();
        subject = PublishSubject.create();
        this.loggedIn = loggedIn;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/InterpreterServiceDialog.fxml"));
        loader.setControllerFactory(c -> {
            InterpreterRequestController controller = new InterpreterRequestController(loggedIn);
            controller.dialogCompleted().subscribe(
                    next -> {
                        if(next.result() == DialogResult.OK){
                            DatabaseController dbc = new DatabaseController();
                            String serviceType = "INTR";
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

    public static Observable<DialogEvent<InterpreterServiceRequest>> showDialog(Employee employee) {
        if (employee == null) ThrowHelper.illegalNull("employee");

        InterpreterRequestScreen screen = new InterpreterRequestScreen(employee);
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
