package edu.wpi.cs3733.c20.teamS;

import edu.wpi.cs3733.c20.teamS.Editing.MapEditingScreen;
import edu.wpi.cs3733.c20.teamS.app.DialogResult;
import edu.wpi.cs3733.c20.teamS.serviceRequests.Employee;
import edu.wpi.cs3733.c20.teamS.serviceRequests.SelectServiceController;
import edu.wpi.cs3733.c20.teamS.serviceRequests.SelectServiceScreen;
import edu.wpi.cs3733.c20.teamS.twoFactor.TwoFactorScreen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreen {
    private final Stage stage;
    private final Stage toPass;
    private final Scene scene;

    private LoginScreen(Stage mainStage) {
        this.stage = new Stage();
        this.toPass = mainStage;

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/loginScreen.fxml"));
        loader.setControllerFactory(e -> {
            loginScreenController cont = new loginScreenController();
            cont.dialogCompleted().subscribe(
                    next -> {
                        if(next.result()== DialogResult.OK){
                            //Intercepthere
                            //Scene twoFactorScene = new Scene();
                            //stage.show
                            TwoFactorScreen mes = new TwoFactorScreen(toPass, next.value());

                        }
                        this.stage.close();
            });
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

    public static void showDialog(Stage mainScreen) {
        LoginScreen screen = new LoginScreen(mainScreen);
        screen.show();
    }
}

