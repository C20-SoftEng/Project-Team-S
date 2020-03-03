package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public final class RoomEditController implements Initializable {
    private final PublishSubject<Object> okClicked = PublishSubject.create();
    private final PublishSubject<Object> cancelClicked = PublishSubject.create();

    public RoomEditController() {

    }
    @Override public void initialize(URL location, ResourceBundle resources) {

    }

    public String name() {
        return nameTextField.getText();
    }
    public void setName(String value) {
        nameTextField.setText(value);
    }
    public String description() {
        return descriptionTextArea.getText();
    }
    public void setDescription(String value) {
        descriptionTextArea.setText(value);
    }
    public String iconPath() {
        return iconPathTextField.getText();
    }
    public void setIconPath(String value) {
        iconPathTextField.setText(value);
    }
    public Observable<Object> okClicked() {
        return okClicked;
    }
    public Observable<Object> cancelClicked() {
        return cancelClicked;
    }

    //region UI controls
    @FXML private JFXTextField nameTextField;
    @FXML private JFXTextArea descriptionTextArea;
    @FXML private JFXTextField iconPathTextField;
    @FXML private void onCancelClicked() {
        cancelClicked.onNext(new Object());
    }
    @FXML private void onOKClicked() {
        okClicked.onNext(new Object());
    }
    //endregion
}
