package edu.wpi.cs3733.c20.teamS.utilities.rx;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import io.reactivex.rxjava3.core.Observable;

/**
 * A read-only wrapper around a reactive property.
 * @param <T>
 */
public class ReadOnlyReactiveProperty<T> {
    private final ReactiveProperty<T> inner;
    public ReadOnlyReactiveProperty(ReactiveProperty<T> inner) {
        if (inner == null) ThrowHelper.illegalNull("inner");

        this.inner = inner;
    }

    public T value() {
        return inner.value();
    }
    public Observable<T> changed() {
        return inner.changed();
    }
}
