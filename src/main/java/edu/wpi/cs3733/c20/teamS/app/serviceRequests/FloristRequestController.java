package edu.wpi.cs3733.c20.teamS.app.serviceRequests;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.app.DialogEvent;
import edu.wpi.cs3733.c20.teamS.serviceRequests.FloristServiceRequest;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.util.LinkedList;

public class FloristRequestController {

    private final PublishSubject<DialogEvent<FloristServiceRequest>> dialogCompleted_ = PublishSubject.create();
    private FloristServiceRequest request = new FloristServiceRequest();
    private Employee loggedIn;
    private int numFlowers;
    private LinkedList<String> flowers;

    @FXML
    private JFXTextField locationField;

    @FXML
    private JFXTextArea commentsField;

    @FXML
    private JFXTextField number;
    @FXML
    private Label greed;

    @FXML
    private JFXCheckBox Hydrangeas;
    @FXML
    private JFXCheckBox Lilies;
    @FXML
    private JFXCheckBox Roses;
    @FXML
    private JFXCheckBox Daisies;
    @FXML
    private JFXCheckBox Poppies;
    @FXML
    private JFXCheckBox Orchids;

    @FXML
    private JFXTextField flowerNum;


    @FXML
    void onCancelClicked(ActionEvent event) {
        dialogCompleted_.onNext(DialogEvent.cancel());
    }

    @FXML
    void onSubmitClicked(ActionEvent event) {
        if (locationField.getText() != null) {

            if (Hydrangeas.isSelected() == true) {
                flowers.add(Hydrangeas.getText());
            }
            if (Lilies.isSelected() == true) {
                flowers.add(Lilies.getText());
            }
            if (Roses.isSelected() == true) {
                flowers.add(Roses.getText());
            }
            if (Daisies.isSelected() == true) {
                flowers.add(Daisies.getText());
            }
            if (Poppies.isSelected() == true) {
                flowers.add(Poppies.getText());
            }
            if (Orchids.isSelected() == true) {
                flowers.add(Orchids.getText());
            }
        }
        if (flowers.size() >= 6)
            greed.setVisible(true);

        request.setFlowerTypes_(request.getFlowers());
        request.setNumFlowers_(numFlowers);
        request.setLocation(locationField.getText());
        request.setMessage(commentsField.getText());
        request.assignTo(loggedIn);

        dialogCompleted_.onNext(DialogEvent.ok(request));
    }
}
