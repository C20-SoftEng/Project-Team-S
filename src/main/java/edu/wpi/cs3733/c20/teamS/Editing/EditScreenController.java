package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

public class EditScreenController {
    @FXML private JFXRadioButton addNode;
    @FXML private ScrollPane scrollPane;
    public EditScreenController() {}
    public JFXRadioButton getAddNode() {return addNode;}
    public ScrollPane getScrollPane() {return scrollPane;}
}
