package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class NodeEditController implements Initializable {

    @FXML private Label errored;
    @FXML private ImageView nodeError;
    @FXML private ImageView fullError;
    @FXML private ImageView shortError;

    @FXML private JFXTextField fullName;
    @FXML private JFXTextField shortName;
    @FXML private JFXComboBox<String> nodeType;

    /**
     * There might be a better way to display errors, but this works. Feel free to refactor.
     */
    @FXML private void onOKClicked() {
        //Verifiers
        nodeType(); fullName(); shortName();
        if (!((nodeType().equals("Error"))
                || (fullName().equals("Error")) || (shortName().equals("Error"))))
            okClicked.onNext(this);
    }

    @FXML private void onCancelClicked() {
        cancelClicked.onNext(this);
    }


    private PublishSubject<Object> okClicked = PublishSubject.create();
    private PublishSubject<Object> cancelClicked = PublishSubject.create();

    public NodeEditController() {}

    public String nodeType() {
        if (nodeType.getValue() == null){
            errored.setVisible(true);
            nodeError.setVisible(true);
//            System.out.println("nulled" + nodeType.getValue());
            return "Error";
        } else {
            nodeError.setVisible(false);
//            System.out.println("Elsed");
            return nodeType.getValue();
        }
    }
    public void setNodeType(String value) {
        nodeType.setValue(value);
    }
    public String fullName() {
        if (fullName.getText().equals("")){
            errored.setVisible(true);
            fullError.setVisible(true);
            return "Error";
        } else {
            fullError.setVisible(false);
            return fullName.getText();
        }
    }
    public void setFullName(String value) {
        fullName.setText(value);
    }

    public String shortName() {
        if (shortName.getText().equals("")) {
            errored.setVisible(true);
            shortError.setVisible(true);
            return "Error";
        } else {
            shortError.setVisible(false);
            return shortName.getText();
        }
    }
    public void setShortName(String value) {
        shortName.setText(value);
    }

    public Observable<Object> okClicked() {
        return okClicked;
    }
    public Observable<Object> cancelClicked() {
        return cancelClicked;
    }

    public void initialize(URL url, ResourceBundle rb)  {

        nodeType.getItems().addAll("HALL", "DEPT", "CONF",
                "SERV", "RETL", "INFO",
                "LABS", "ELEV", "STAI", "EXIT");

    }
}
