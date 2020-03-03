package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.collisionMasks.Room;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public final class RoomEditController implements Initializable {
    private final Room room;

    public RoomEditController(Room room) {
        if (room == null) ThrowHelper.illegalNull("room");

        this.room = room;
    }
    @Override public void initialize(URL location, ResourceBundle resources) {

    }

    //region UI controls
    @FXML private JFXTextField nameTextField;
    @FXML private JFXTextArea descriptionTextArea;
    @FXML private JFXTextField iconPathTextField;


    //endregion
}
