package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class NodeEditController implements Initializable {

    @FXML private Label errored;
    @FXML private ImageView floorError;
    @FXML private ImageView buildingError;
    @FXML private ImageView nodeError;
    @FXML private ImageView fullError;
    @FXML private ImageView shortError;
    @FXML private JFXTextField floorNumber;
    @FXML private JFXTextField buildingName;
    @FXML private JFXTextField fullNodeName;
    @FXML private JFXTextField shortNodeName;
    @FXML private JFXComboBox<String> nodeType;

    /**
     * There might be a better way to display errors, but this works. Feel free to refactor.
     */
    @FXML private void onOKClicked() {
        //Verifiers
        floorNumber(); buildingName(); nodeType(); fullNodeName(); shortNodeName();
        if (!((floorNumber() == -1) ||
                (buildingName().equals("Error")) || (nodeType().equals("Error"))
                || (fullNodeName().equals("Error")) || (shortNodeName().equals("Error"))))
            okClicked.onNext(this);
    }

    @FXML private void onCancelClicked() {
        cancelClicked.onNext(this);
    }


    private PublishSubject<Object> okClicked = PublishSubject.create();
    private PublishSubject<Object> cancelClicked = PublishSubject.create();

    public NodeEditController() {}

    public int floorNumber() {
        if (floorNumber.getText().equals("")) {
            errored.setVisible(true);
            floorError.setVisible(true);
            return -1;
        } else {
            floorError.setVisible(false);
            return Integer.parseInt(floorNumber.getText());
        }
    }

    public String buildingName() {
        if (buildingName.getText().equals("")){
            errored.setVisible(true);
            buildingError.setVisible(true);
            return "Error";
        } else {
            buildingError.setVisible(false);
            return buildingName.getText();
        }
    }

    public String nodeType() {
//        System.out.println("nodetype reached");
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

    public String fullNodeName() {
        if (fullNodeName.getText().equals("")){
            errored.setVisible(true);
            fullError.setVisible(true);
            return "Error";
        } else {
            fullError.setVisible(false);
            return fullNodeName.getText();
        }
    }

    public String shortNodeName() {
        if (shortNodeName.getText().equals("")) {
            errored.setVisible(true);
            shortError.setVisible(true);
            return "Error";
        } else {
            shortError.setVisible(false);
            return shortNodeName.getText();
        }
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
