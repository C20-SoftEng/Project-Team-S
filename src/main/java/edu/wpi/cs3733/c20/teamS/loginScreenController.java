package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
import edu.wpi.cs3733.c20.teamS.serviceRequests.AccessLevel;
import edu.wpi.cs3733.c20.teamS.serviceRequests.DrugServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.derby.iapi.db.Database;
import org.apache.derby.iapi.db.DatabaseContext;

import java.io.IOException;

public class loginScreenController {
    @FXML private JFXTextField id;
    @FXML private JFXPasswordField pw;
    @FXML private JFXButton cancel;
    @FXML private JFXButton enter;
    @FXML private Label wrongStuff;
    @FXML private AnchorPane anchor;


    @FXML void onCancelClicked(ActionEvent event) {
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    /**
     * change this for later, just using admin
     */
    @FXML void onEnterClicked(ActionEvent event) throws IOException {
        DatabaseController dc = new DatabaseController();
        boolean validLogin = dc.checkLogin(id.getText(), pw.getText());
        if (validLogin){
            EmployeeData ed = dc.getEmployee(id.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/twoFactorScreen.fxml"));
            //TwoFactorScreenController twoFac = loader.getController();
            AnchorPane twoFactor = loader.load();
            //AnchorPane twoFactor = FXMLLoader.load();
            anchor.getChildren().setAll(twoFactor);
//            AccessLevel[] al = AccessLevel.values();
//            AccessLevel accessLevel = al[ed.getAccessLevel()];
//            Employee emp = new Employee(ed.getEmployeeID(), ed.getFirstName()+ " " + ed.getLastName(), accessLevel);
//            dialogCompleted_.onNext(DialogEvent.ok(emp));
        }
        else {
            wrongStuff.setVisible(true);
        }
    }

    public Observable<DialogEvent<Employee>> dialogCompleted() {
        return dialogCompleted_;
    }
    private final PublishSubject<DialogEvent<Employee>> dialogCompleted_ = PublishSubject.create();

}
