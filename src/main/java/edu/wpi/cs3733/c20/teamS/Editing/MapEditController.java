package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MapEditController {
    @FXML private JFXTextField floor;
    @FXML private JFXTextField building;
    @FXML private JFXTextField nodeType;
    @FXML private JFXTextField longName;
    @FXML private JFXTextField shortName;
    @FXML private JFXButton cancelButton;
    @FXML private JFXButton confirmButton;

    public MapEditController() {}

    public JFXTextField getFloor() {return floor;}
    public JFXTextField getBuilding() {return building;}
    public JFXTextField getNodeType() {return nodeType;}
    public JFXTextField getLongName() {return longName;}
    public JFXTextField getShortName() {return shortName;}

    public Button getCancel() {return cancelButton;}
    public Button getOk() {return confirmButton;}
}
