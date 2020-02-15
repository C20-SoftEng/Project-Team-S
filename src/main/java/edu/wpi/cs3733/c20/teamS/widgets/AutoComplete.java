package edu.wpi.cs3733.c20.teamS.widgets;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import javafx.scene.control.TextField;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * A utility class containing functions for providing autocomplete services. To begin providing autocomplete
 * services for a TextField, simply call one of the start() overloads that takes a TextField.
 */
public final class AutoComplete {
    private AutoComplete() {}

    static Observable<Collection<String>> createResultStream(
            Collection<String> dictionary,
            Observable<String> textInput) {
        if (dictionary == null) throw new IllegalArgumentException("'dictionary' can't be null.");
        if (textInput == null) throw new IllegalArgumentException("'textInput' can't be null.");

        return textInput.map(text -> wordLookup(dictionary, text));
    }

    private static Collection<String> wordLookup(Collection<String> dictionary, String text) {
        if (text == null || text.isEmpty())
            return Collections.emptyList();

        return dictionary.stream()
                .filter(word -> word.contains(text))
                .collect(Collectors.toList());
    }

    /**
     * Begins providing autocomplete services for the specified input stream.
     * @param dictionary The corpus of words or phrases that autocomplete will search.
     * @param textInput The source of text input events from the user.
     * @param populateResults A collection to populate with results from autocomplete. Each time the user
     *                        enters text, this collection will be cleared and repopulated with the results for whatever
     *                        text the user entered.
     */
    public static void start(
            Collection<String> dictionary,
            Observable<String> textInput,
            Collection<String> populateResults
    ) {
        Observable<Collection<String>> resultStream = createResultStream(dictionary, textInput);

        resultStream.subscribe(results -> {
            populateResults.clear();
            populateResults.addAll(results);
        });
    }

    /**
     * Begins providing autocomplete services for the specified TextField.
     * @param dictionary The corpus of words or phrases to search from.
     * @param inputField The TextField that the user is typing in.
     * @param populateResults The collection to populate with autocomplete results. For example, to populate a ListView
     *                        with autocomplete results, you would pass listView.getChildren() as this argument.
     *                        The collection will be cleared and repopulated with autocomplete results every time
     *                        the user enters text.
     */
    public static void start(
            Collection<String> dictionary,
            TextField inputField,
            Collection<String> populateResults
    ) {
        Subject<String> inputStream = PublishSubject.create();
        inputField.textProperty().addListener((sender, old, current) -> inputStream.onNext(current));

        start(dictionary, inputStream, populateResults);
    }
}
