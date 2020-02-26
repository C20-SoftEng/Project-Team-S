package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.utilities.SendEmailDirectionsThread;
import edu.wpi.cs3733.c20.teamS.utilities.SendTextDirectionsThread;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class SendTextDirectionsScreen {

    private final Stage stage;
    private final Scene scene;


    private SendTextDirectionsScreen(List<String> directions) {
        this.stage = new Stage();
        //this.loggedIn = employee;

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SendDirectionsDialog.fxml"));
        loader.setControllerFactory(e -> {
            SendTextDirectionsController cont = new SendTextDirectionsController(directions);
            return cont;
        });

        try {
            Parent root = loader.load();
            scene = new Scene(root);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void show() {
        stage.setScene(scene);
        stage.show();
    }



    public static void showDialog(List<String> directions) {
        SendTextDirectionsScreen screen = new SendTextDirectionsScreen(directions);
        screen.show();
    }

    public void runEmailThread(Set<String> directions, String email){
        SendEmailDirectionsThread SEDT = new SendEmailDirectionsThread(directions,email);
        SEDT.run();

    }

    public void runTextThread(Set<String> directions, String number, String carrier){
        SendTextDirectionsThread STDT = new SendTextDirectionsThread(directions,number,carrier);
        STDT.run();

    }

}
