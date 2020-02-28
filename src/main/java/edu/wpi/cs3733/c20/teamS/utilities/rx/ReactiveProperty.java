package edu.wpi.cs3733.c20.teamS.utilities.rx;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.Objects;

/**
 * A property that exposes an observable changed event stream.
 * @param <T> The type of value.
 */
public final class ReactiveProperty<T> {
    private final PublishSubject<T> changed = PublishSubject.create();
    private T value;
    private ReadOnlyReactiveProperty<T> readOnlyWrapper;

    public ReactiveProperty(T value) {
        this.value = value;
    }

    /**
     * The current value of the reactive property.
     */
    public T value() {
        return value;
    }

    /**
     * Sets the value of the reactive property. This will raise a changed() event if
     * the new value is different from the old one.
     * @param value
     */
    public void setValue(T value) {
        T previous = this.value;
        if (Objects.equals(previous, value))
            return;
        this.value = value;
        changed.onNext(value);
    }

    /**
     * Observable stream that gets events pushed to it whenever the value of the property changes.
     * @return
     */
    public Observable<T> changed() {
        return changed;
    }

    public ReadOnlyReactiveProperty<T> asReadOnly() {
        if (readOnlyWrapper == null)
            readOnlyWrapper = new ReadOnlyReactiveProperty<>(this);
        return readOnlyWrapper;
    }
}