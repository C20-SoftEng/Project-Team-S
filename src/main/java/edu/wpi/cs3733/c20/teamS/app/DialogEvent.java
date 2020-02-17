package edu.wpi.cs3733.c20.teamS.app;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public final class DialogEvent<T> {
    private final DialogResult result_;
    private final T value_;

    private DialogEvent(DialogResult result, T value) {
        this.result_ = result;
        this.value_ = value;
    }

    public T value() {
        return value_;
    }
    public DialogResult result() {
        return result_;
    }

    public static <T> DialogEvent<T> ok(T value) {
        throw new NotImplementedException();
    }
    public static <T> DialogEvent<T> cancel() {
        throw new NotImplementedException();
    }
}
