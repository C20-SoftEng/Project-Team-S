package edu.wpi.cs3733.c20.teamS.newellsSecretTestLab.autocomplete;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public final class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Collection<String> dictionary = loadWordsFile();
        AutoCompleteComboBoxScreen screen = new AutoCompleteComboBoxScreen(primaryStage, dictionary);
        screen.show();
    }

    private Collection<String> loadWordsFile() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("newellsSecretTestLab/words.txt");
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);

        return br.lines()
                .collect(Collectors.toList());
    }


}
