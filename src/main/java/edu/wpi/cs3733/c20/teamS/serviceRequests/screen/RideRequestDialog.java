package edu.wpi.cs3733.c20.teamS.serviceRequests.screen;

import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.UIControllers.RideRequestUIController;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class RideRequestDialog {
    private static final String path_ = "FXML/serviceRequests/RideRequestDialog.fxml";

    private RideRequestDialog()  {}

    public static Observable<DialogEvent<RideRequest>> showDialog(Stage stage) {
        RideRequestDialog dialog = new RideRequestDialog();
        FXMLLoader loader = new FXMLLoader(dialog.getClass().getClassLoader().getResource(path_));
        PublishSubject<DialogEvent<RideRequest>> subject = PublishSubject.create();

        loader.setControllerFactory(c -> {
            RideRequestUIController controller = new RideRequestUIController();
            controller.completed().subscribe(r -> subject.onNext(DialogEvent.okEvent(r)));
            controller.canceled().subscribe(r -> subject.onNext(DialogEvent.cancelEvent()));
            return controller;
        });

        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return subject;
    }
}
