package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class MapEditController {
    @FXML private JFXTextField floorNumber;
    @FXML private JFXTextField buildingName;
    @FXML private JFXTextField nodeType;
    @FXML private JFXTextField fullNodeName;
    @FXML private JFXTextField shortNodeName;
    @FXML private JFXButton cancelButton;
    @FXML private JFXButton confirmButton;

    public MapEditController() {}

    public JFXTextField getFloor() {return floorNumber;}
    public JFXTextField getBuilding() {return buildingName;}
    public JFXTextField getNodeType() {return nodeType;}
    public JFXTextField getLongName() {return fullNodeName;}
    public JFXTextField getShortName() {return shortNodeName;}

    public JFXButton getCancel() {return cancelButton;}
    public JFXButton getOk() {return confirmButton;}
}
