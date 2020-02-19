package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.database.DatabaseController;
import edu.wpi.cs3733.c20.teamS.database.EmployeeData;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.derby.iapi.db.Database;
import org.apache.derby.iapi.db.DatabaseContext;

public class loginScreenController {

    @FXML private JFXTextField id;

    @FXML private JFXPasswordField pw;

    @FXML private JFXButton cancel;

    @FXML private JFXButton enter;

    @FXML private Label wrongID;

    @FXML private Label wrongPW;

    @FXML void onCancelClicked(ActionEvent event) {
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    /**
     * change this for later, just using admin
     */
    @FXML void onEnterClicked(ActionEvent event) {
        DatabaseController dc = new DatabaseController();
        boolean validLogin = dc.checkLogin(id.getText(), pw.getText());
        if (validLogin){
            EmployeeData ed = dc.getEmployee(id.getText());
            Employee emp = new Employee(ed.getEmployeeID(), ed.getFirstName()+ " " + ed.getLastName());
            dialogCompleted_.onNext(DialogEvent.ok(emp));
        }
        if (!(id.getText().equals("admin"))){
            wrongID.setVisible(true);
        }
        if (!pw.getText().equals("admin")){
            wrongPW.setVisible(true);
        }
    }

    public Observable<DialogEvent<Employee>> dialogCompleted() {
        return dialogCompleted_;
    }
    private final PublishSubject<DialogEvent<Employee>> dialogCompleted_ = PublishSubject.create();

}
