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

    boolean isMyComboBoxEmpty = nodeType.getSelectionModel().isEmpty();

    @FXML private void onOKClicked() {
        if (!isMyComboBoxEmpty){
            errored.setVisible(true);
        }
        else {
            okClicked.onNext(this);
        }
    }
    @FXML private void onCancelClicked() {
        cancelClicked.onNext(this);
    }


    private PublishSubject<Object> okClicked = PublishSubject.create();
    private PublishSubject<Object> cancelClicked = PublishSubject.create();

    public NodeEditController() {}

    public int floorNumber() {
        return Integer.parseInt(floorNumber.getText());
    }
    public String buildingName() {
        return buildingName.getText();
    }
    public String nodeType() {
            return nodeType.getValue();
    }
    public String fullNodeName() {
        return fullNodeName.getText();
    }
    public String shortNodeName() {
        return shortNodeName.getText();
    }

    public Observable<Object> okClicked() {
//        if(!buildingName().equals("") && !nodeType().equals("") && !fullNodeName().equals("") && !shortNodeName().equals("")){
//            return okClicked;
//        }
//        else {
//            return null;
//        }
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
