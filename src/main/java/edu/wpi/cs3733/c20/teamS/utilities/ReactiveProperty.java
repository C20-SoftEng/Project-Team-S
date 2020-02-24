package edu.wpi.cs3733.c20.teamS.utilities;

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

    public ReactiveProperty(T value) {
        this.value = value;
    }
    public T value() {
        return value;
    }
    public void setValue(T value) {
        T previous = this.value;
        if (!Objects.equals(previous, value))
            return;
        this.value = value;
        changed.onNext(value);
    }
    public Observable<T> changed() {
        return changed;
    }
}