package edu.wpi.cs3733.c20.teamS.widgets;

import edu.wpi.cs3733.c20.teamS.Testing;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import javax.swing.tree.DefaultTreeCellEditor;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AutoCompleteTests {
    protected final List<String> dictionary = Arrays.asList("abby", "abe", "babe", "lit", "shit", "blitz");
    protected final PublishSubject<String> input = PublishSubject.create();

    private class Logger {
        public List<Collection<String>> results = new ArrayList<>();

        public Logger(Observable<Collection<String>> source) {
            source.subscribe(strings -> results.add(strings));
        }
    }

    public static class StartFactoryMethod extends AutoCompleteTests {
        private final List<String> results;

        public StartFactoryMethod() {
            results = new ArrayList<>();
            AutoComplete.start(dictionary, input, results);
        }

        @Test
        public void start_emptyResultsCollection_isPopulated() {
            input.onNext("li");

            assertEquals(Arrays.asList("lit", "blitz"), results);
        }

        @Test
        public void start_nonEmptyResultsCollection_isClearedAndRepopulated() {
            results.addAll(Arrays.asList("shitty", "witty", "mitty", "gritty"));

            input.onNext("abe");

            assertEquals(Arrays.asList("abe", "babe"), results);
        }
    }
}
