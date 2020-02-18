package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class DrugRequestController {

    private final PublishSubject<DialogEvent<DrugServiceRequest>> dialogCompleted_ = PublishSubject.create();
}
