package edu.wpi.cs3733.c20.teamS;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Simple screen for main client ui
 */
public class mainToLoginScreen {
    private mainScreenController ui;
    private Scene scene;
    private Stage stage;

    public mainToLoginScreen(Stage stage) {
        //if (stage==null) ThrowHelper.illegalNull("stage");

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/UI_client.fxml"));
        loader.setControllerFactory(c -> {
            this.ui = new mainScreenController();
            return this.ui;
        });

        try {
            Parent root = loader.load();

            // ui.setHelpStyle();
            this.scene = new Scene(root);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    /*    ui.staffClicked().subscribe(e->{
            NewLoginScreen.showLogin().subscribe( f ->{
                if(f.result()==DialogResult.OK){
                    ServiceRequestScreen test = new ServiceRequestScreen(stage, f.value());
                }
                else{
                    this.show();
                }

            } );
        });
        */
      /*
        ui.helpClicked().subscribe(e->{
            TutorialScreen.showTutorial(stage).subscribe((g -> {
                this.show();
            }));

        });
        this.show();
    }
*//*
        public void show() {
            stage.setScene(scene);
            stage.show();
        }*/
    }
}
