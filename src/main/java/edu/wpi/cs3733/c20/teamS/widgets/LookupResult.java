package edu.wpi.cs3733.c20.teamS.widgets;

public final class LookupResult<T> {
    private final T value;
    private final String text;

    LookupResult(String text, T value) {
        this.text = text;
        this.value = value;
    }

    public String text() {
        return text;
    }
    public T value() {
        return value;
    }

    @Override
    public String toString() {
        return text;
    }
}
