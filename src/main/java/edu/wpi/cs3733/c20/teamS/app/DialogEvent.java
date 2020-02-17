package edu.wpi.cs3733.c20.teamS.app;

public final class DialogEvent<T> {
    private final DialogResult result_;
    private final T value_;

    private DialogEvent(DialogResult result, T value) {
        this.result_ = result;
        this.value_ = value;
    }
    public DialogResult result() {
        return result_;
    }
    public T value() {
        return value_;
    }

    public static <T> DialogEvent<T> cancelEvent() {
        return new DialogEvent<>(DialogResult.CANCEL, null);
    }

    public static <T> DialogEvent<T> okEvent(T value) {
        return new DialogEvent<>(DialogResult.OK, value);
    }
}
