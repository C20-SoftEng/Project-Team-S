package edu.wpi.cs3733.c20.teamS.newellsSecretTestLab.autocomplete;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;

public class AutoCompleteComboBoxScreen {
    private final Stage stage_;
    private final Scene scene_;
    private static final String path_ = "newellsSecretTestLab/AutoCompleteComboBox.fxml";

    public AutoCompleteComboBoxScreen(Stage stage, Collection<String> dictionary) {
        this.stage_ = stage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(path_));
            loader.setControllerFactory(c -> new AutoCompleteComboBoxUIController(dictionary));
            Parent root = loader.load();
            scene_ = new Scene(root);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void show() {
        stage_.setScene(scene_);
        stage_.show();
    }
}
