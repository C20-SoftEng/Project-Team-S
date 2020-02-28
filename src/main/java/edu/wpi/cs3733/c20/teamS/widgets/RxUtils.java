package edu.wpi.cs3733.c20.teamS.widgets;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import javafx.beans.property.Property;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.function.Consumer;

/**
 * Contains utility methods for working with RX streams. In particular, it contains methods to convert various
 * JavaFX events into observable streams.
 */
public final class RxUtils {
    private RxUtils() {}

    public static <T> Observable<T> propertyStream(Property<T> property) {
        if (property == null) ThrowHelper.illegalNull("property");

        Subject<T> subject = PublishSubject.create();
        property.addListener((sender, previous, current) -> subject.onNext(current));

        return subject;
    }

    /**
     * Creates an observable stream from the setter for a JavaFX event.
     * @param setListener The function that you call to set an event handler, for example, setOnMouseClicked(...)
     * @param <T> The type of event.
     * @return An observable stream for the event.
     */
    public static <T extends Event> Observable<T> eventStream(Consumer<EventHandler<T>> setListener) {
        PublishSubject<T> result = PublishSubject.create();
        setListener.accept(e -> result.onNext(e));
        return result;
    }
}
