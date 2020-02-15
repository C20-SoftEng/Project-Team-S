package edu.wpi.cs3733.c20.teamS;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.c20.teamS.events.EventData;
import edu.wpi.cs3733.c20.teamS.events.IEvent;
import edu.wpi.cs3733.c20.teamS.events.IEventData;
import edu.wpi.cs3733.c20.teamS.events.IPublisher;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Very basic controller for client screen
 */
public class mainScreenController implements Initializable {
    @FXML private JFXButton staffButton;
    @FXML private JFXButton helpButton;
    @FXML private JFXTextField searchBar;
    // private ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.createFont());
    @FXML private void onStaffClicked() {
        staffClicked_.raise(new EventData(this));
    }
    @FXML private void onHelpClicked() { helpClicked_.raise(new EventData(this));}
   // public JFXButton getHelpClicked() {return helpButton;}
    //public ImageView backgroundMap;


    private final IPublisher<IEventData> staffClicked_ = IPublisher.create();
    private final IPublisher<IEventData> helpClicked_ = IPublisher.create();

    public mainScreenController() {
    }

    public IEvent<IEventData> staffClicked(){
        return staffClicked_.event();
    }
    public IEvent<IEventData> helpClicked(){
        return helpClicked_.event();
    }
    public void setHelpStyle(){
     /*   helpButton.setStyle("-fx-background-image: url(' /images/Icons/Help_Icon.png')");
        helpButton.setStyle("-fx-background-size: 70px 70px");
        helpButton.setStyle("-fx-background-position: ");
*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        helpButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


    }
}

