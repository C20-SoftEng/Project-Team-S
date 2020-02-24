package edu.wpi.cs3733.c20.teamS.widgets;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import javafx.beans.property.Property;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A utility class containing functions for providing autocomplete services. To begin providing autocomplete
 * services for a TextField, simply call one of the start() overloads that takes a TextField.
 */
public final class AutoComplete {
    private AutoComplete() {}

    private static Collection<String> wordLookup(Collection<String> dictionary, String text) {
        if (text == null || text.isEmpty())
            return Collections.emptyList();

        return dictionary.stream()
                .filter(word -> word.contains(text))
                .collect(Collectors.toList());
    }

    public static <T> Observable<T> propertyStream(Property<T> property) {
        Subject<T> subject = PublishSubject.create();
        property.addListener((sender, previous, current) -> subject.onNext(current));

        return subject;
    }

    /**
     * ADVANCED USERS ONLY
     * Begins providing autocomplete services for the specified input stream.
     * @param dictionary The corpus of words or phrases that autocomplete will search.
     * @param textInput The source of text input events from the user.
     * @param populateResults A collection to populate with results from autocomplete. Each time the user
     *                        enters text, this collection will be cleared and repopulated with the results for whatever
     *                        text the user entered.
     * @return An object that can be used to cancel the autocomplete service.
     */
    public static Disposable start(
            Collection<String> dictionary,
            Observable<String> textInput,
            Collection<String> populateResults
    ) {
        return textInput
                .map(text -> wordLookup(dictionary, text))
                .subscribe(words -> {
                    populateResults.clear();
                    populateResults.addAll(words);
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
     * @return An object that can be used to cancel the auto-complete service.
     */
    public static Disposable start(
            Collection<String> dictionary,
            TextField inputField,
            Collection<String> populateResults
    ) {
        Observable<String> inputStream = propertyStream(inputField.textProperty());
        return start(dictionary, inputStream, populateResults);
    }

    /**
     * Begins providing autocomplete services to the specified ComboBox. The combo-box's item collection
     * will be populated with the autocomplete results.
     * @param dictionary The collection of strings to search for autocomplete results in.
     * @param inputComboBox The ComboBox that the user will be typing in.
     * @param maxResultsDisplay The maximum number of rows to display in the ComboBox dropdown.
     * @return An object that can be used to cancel the autocomplete service.
     */
    public static Disposable start(
            Collection<String> dictionary,
            ComboBox<String> inputComboBox,
            int maxResultsDisplay
    ) {
        Observable<String> inputStream = propertyStream(inputComboBox.getEditor().textProperty());
        return inputStream
                .map(text -> wordLookup(dictionary, text))
                .subscribe(words -> {
                    List<String> items = inputComboBox.getItems();
                    items.clear();
                    items.addAll(words);
                    int rowCount = Math.min(words.size(), maxResultsDisplay);
                    inputComboBox.setVisibleRowCount(rowCount);
                    if (items.isEmpty())
                        inputComboBox.hide();
                    else
                        inputComboBox.show();
                });
    }

    /**
     * Begins providing autocomplete services to the specified ComboBox. The combo-box's item collection
     * will be populated with the autocomplete results.
     * @param dictionary The collection of strings to search for autocomplete results in.
     * @param inputComboBox The ComboBox that the user will be typing in.
     * @return An object that can be used to stop receiving autocomplete services.
     */
    public static Disposable start(Collection<String> dictionary, ComboBox<String> inputComboBox ) {
        return start(dictionary, inputComboBox, 10);
    }

    public static <T> Observable<Collection<LookupResult<T>>> createLookupStream(
            Collection<T> dictionary,
            Observable<String> textInput,
            Function<T, String> textExtractor
    ) {
        if (dictionary == null) ThrowHelper.illegalNull("dictionary");
        if (textInput == null) ThrowHelper.illegalNull("textInput");
        if (textExtractor == null) ThrowHelper.illegalNull("textExtractor");

        Map<String, T> lookup = dictionary.stream()
                .collect(Collectors.toMap(textExtractor, t -> t));

        return textInput
                .map(text -> wordLookup(lookup.keySet(), text))
                .map(words -> words.stream()
                        .map(word -> new LookupResult<>(word, lookup.get(word)))
                        .collect(Collectors.toList()));
    }

}
