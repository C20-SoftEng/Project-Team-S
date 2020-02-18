package edu.wpi.cs3733.c20.teamS.Editing;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MapEditController {
    @FXML private JFXTextField floor;
    @FXML private JFXTextField building;
    @FXML private JFXTextField nodeType;
    @FXML private JFXTextField longName;
    @FXML private JFXTextField shortName;
    @FXML private Button cancel;
    @FXML private Button ok;

    public MapEditController() {}

    public JFXTextField getFloor() {return floor;}
    public JFXTextField getBuilding() {return building;}
    public JFXTextField getNodeType() {return nodeType;}
    public JFXTextField getLongName() {return longName;}
    public JFXTextField getShortName() {return shortName;}

    public Button getCancel() {return cancel;}
    public Button getOk() {return ok;}
}
