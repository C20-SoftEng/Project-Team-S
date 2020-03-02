package edu.wpi.cs3733.c20.teamS.serviceRequests;

import edu.wpi.cs3733.c20.teamS.BaseScreen;
import edu.wpi.cs3733.c20.teamS.Settings;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectServiceScreen {

    private final Stage stage;
    private final Scene scene;
    private final Employee loggedIn;

    private SelectServiceScreen(Employee employee) {
        if(employee == null) ThrowHelper.illegalNull("employee");
        this.stage = new Stage();
        this.loggedIn = employee;

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SelectServiceScreen.fxml"));
        loader.setControllerFactory(e -> {
            SelectServiceController cont = new SelectServiceController(stage, loggedIn);
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
        Settings.openWindows.add(this.stage);
        BaseScreen.puggy.register(scene, Event.ANY);
        stage.show();
    }

    public static void showDialog(Employee loggedIn) {
        SelectServiceScreen screen = new SelectServiceScreen(loggedIn);
        screen.show();
    }
}


