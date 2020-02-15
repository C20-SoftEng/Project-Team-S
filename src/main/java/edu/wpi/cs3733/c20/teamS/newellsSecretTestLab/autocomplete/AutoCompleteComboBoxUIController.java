package edu.wpi.cs3733.c20.teamS.newellsSecretTestLab.autocomplete;

import edu.wpi.cs3733.c20.teamS.widgets.AutoComplete;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

public class AutoCompleteComboBoxUIController implements Initializable {
    @FXML private ComboBox<String> inputComboBox;
    private final Collection<String> dictionary;

    public AutoCompleteComboBoxUIController(Collection<String> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputComboBox.getItems().addAll(Arrays.asList("Hello", "World", "Everyone"));
        AutoComplete.start(dictionary, inputComboBox);
    }
}
