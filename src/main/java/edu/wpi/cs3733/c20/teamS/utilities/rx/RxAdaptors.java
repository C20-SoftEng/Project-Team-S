package edu.wpi.cs3733.c20.teamS.utilities.rx;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.function.Consumer;

/**
 * Contains adaptor methods for using RX streams with other systems, such as JavaFX events and properties.
 */
public final class RxAdaptors {
    private RxAdaptors() {}

    public static <T> Observable<T> propertyStream(ReadOnlyProperty<T> property) {
        if (property == null) ThrowHelper.illegalNull("property");

        Subject<T> subject = PublishSubject.create();
        property.addListener((sender, previous, current) -> subject.onNext(current));

        return subject;
    }

    public static Observable<Double> propertyStream(ReadOnlyDoubleProperty property) {
        if (property == null) ThrowHelper.illegalNull("property");

        Subject<Double> subject = PublishSubject.create();
        property.addListener((sender, previous, current) -> subject.onNext(current.doubleValue()));

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

    /**
     * Creates an isMouseOver event stream for the specified ui element.
     * @param uiElement The element to create an isMouseOver stream for.
     * @return An observable stream that pushes an event whenever the mouse enters or exits the ui element.
     * True is pushed whenever the mouse is over, false whenever it is not.
     */
    public static ReadOnlyReactiveProperty<Boolean> createMouseOverStream(Node uiElement) {
        if (uiElement == null) ThrowHelper.illegalNull("uiElement");

        ReactiveProperty<Boolean> result = new ReactiveProperty<>(false);
        uiElement.setOnMouseEntered(e -> result.setValue(true));
        uiElement.setOnMouseExited(e -> result.setValue(false));

        return result.asReadOnly();
    }

    /**
     * Creates an observable mouse-dragged event stream that reports the position in parent-coordinates.
     * @param uiElement The ui element to create the mouse-dragged stream for.
     * @return An observable stream that reports the mouse position in parent-coordinates.
     */
    public static Observable<Vector2> createMouseDraggedStream(Node uiElement) {
        return eventStream(uiElement::setOnMouseDragged)
                .map(e -> uiElement.localToParent(e.getX(), e.getY()))
                .map(point -> new Vector2(point.getX(), point.getY()));
    }
}
