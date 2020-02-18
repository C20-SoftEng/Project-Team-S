package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.RideServiceRequest;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class DrugRequestScreen {

    Stage stage;
    private final PublishSubject<DialogEvent<DrugServiceRequest>> subject;

    public DrugRequestScreen(){
        this.stage = new Stage();
        subject = PublishSubject.create();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/serviceRequests/RideRequestDialog.fxml"));
        loader.setControllerFactory(c -> {
            DrugRequestController controller = new DrugRequestController();
            controller.dialogCompleted().subscribe(
                    next -> subject.onNext(next),
                    error -> subject.onError(error),
                    () -> subject.onComplete()
            );
            return controller;
        });
    }
}
