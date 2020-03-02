package edu.wpi.cs3733.c20.teamS.utilities.rx;

import io.reactivex.rxjava3.disposables.Disposable;

import java.util.function.Supplier;

/**
 * When you have a field that is disposable, and you want to make sure that the previous value is
 * disposed when it is assigned a new value, use this.
 * @param <T>
 */
public final class DisposableSelector<T extends Disposable> {
    private T current;

    public T current() {
        return current;
    }
    public void setCurrent(T value) {
        //  Reference equality check intended.
        if (current == value)
            return;
        if (current != null)
            current.dispose();
        current = value;
    }
    public void setCurrent(Supplier<T> factory) {
        if (current != null)
            current.dispose();
        current = factory.get();
    }
}
