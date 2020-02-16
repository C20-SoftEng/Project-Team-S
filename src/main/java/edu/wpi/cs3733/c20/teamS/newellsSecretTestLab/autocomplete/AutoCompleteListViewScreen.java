package edu.wpi.cs3733.c20.teamS.newellsSecretTestLab.autocomplete;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class AutoCompleteListViewScreen {
    private final Stage stage_;
    private final Scene scene_;
    private final String path_ = "newellsSecretTestLab/AutoCompleteListView.fxml";

    public AutoCompleteListViewScreen(Stage stage, Collection<String> dictionary) {
        this.stage_ = stage;

        try {
            URL url = getClass().getClassLoader().getResource(path_);
            FXMLLoader loader = new FXMLLoader(url);
            loader.setControllerFactory(c -> new AutoCompleteListViewUIController(dictionary));
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
