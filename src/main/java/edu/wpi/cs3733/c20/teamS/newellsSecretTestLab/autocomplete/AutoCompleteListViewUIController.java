package edu.wpi.cs3733.c20.teamS.newellsSecretTestLab.autocomplete;

import edu.wpi.cs3733.c20.teamS.widgets.AutoComplete;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class AutoCompleteListViewUIController implements Initializable {
    @FXML private TextField inputTextField;
    @FXML private ListView<String> autoCompleteListView;

    private final Collection<String> dictionary;

    public AutoCompleteListViewUIController(Collection<String> dictionary) {
        this.dictionary = dictionary;

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Collection<String> populateResults = autoCompleteListView.getItems();
        AutoComplete.start(dictionary, inputTextField, populateResults);
    }
}
