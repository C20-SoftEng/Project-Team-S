package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
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

    private RideRequestScreen(Stage stage) {
        this.stage = stage;
        subject = PublishSubject.create();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/RideRequestDialog.fxml"));
        loader.setControllerFactory(c -> {
            RideRequestUIController controller = new RideRequestUIController();
            controller.dialogCompleted().subscribe(
                    next -> subject.onNext(next),
                    error -> subject.onError(error),
                    () -> subject.onComplete()
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

    public static Observable<DialogEvent<RideServiceRequest>> showDialog(Stage stage) {
        if (stage == null) ThrowHelper.illegalNull("stage");

        RideRequestScreen screen = new RideRequestScreen(stage);
        screen.show();

        return screen.subject;
    }
}
