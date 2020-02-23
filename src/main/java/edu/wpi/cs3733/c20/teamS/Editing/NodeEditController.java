package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NodeEditController {
    @FXML private JFXTextField floorNumber;
    @FXML private JFXTextField buildingName;
    @FXML private JFXTextField fullNodeName;
    @FXML private JFXTextField shortNodeName;
    @FXML private void onOKClicked() {
        okClicked.onNext(this);
    }
    @FXML private void onCancelClicked() {
        cancelClicked.onNext(this);
    }
    private String nodeTypings[] =
            {
                    "HALL", "DEPT", "CONF",
                    "SERV", "RETL", "INFO",
                    "LABS", "ELEV", "STAI", "EXIT"
            };

    @FXML private JFXComboBox nodeType = new JFXComboBox();
    private PublishSubject<Object> okClicked = PublishSubject.create();
    private PublishSubject<Object> cancelClicked = PublishSubject.create();

    public NodeEditController() {
        nodeType.getItems().addAll(nodeTypings);
    }

    public int floorNumber() {
        return Integer.parseInt(floorNumber.getText());
    }
    public String buildingName() {
        return buildingName.getText();
    }
    public String nodeType() {
        System.out.println(nodeType.getValue());
        return nodeType.getValue().toString();
    }
    public String fullNodeName() {
        return fullNodeName.getText();
    }
    public String shortNodeName() {
        return shortNodeName.getText();
    }

    public Observable<Object> okClicked() {
        //if(!buildingName().equals("") && !nodeType().equals("") && !fullNodeName().equals("") && !shortNodeName().equals("")){
        //    return okClicked;
        //}else return null;

        return okClicked;
    }
    public Observable<Object> cancelClicked() {
        return cancelClicked;
    }
}
