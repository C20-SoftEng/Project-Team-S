package edu.wpi.cs3733.c20.teamS.app;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

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
        if (value == null) ThrowHelper.illegalNull("value");

        return new DialogEvent<>(DialogResult.OK, value);
    }
    public static <T> DialogEvent<T> cancel() {
        return new DialogEvent<>(DialogResult.CANCEL, null);
    }
}
