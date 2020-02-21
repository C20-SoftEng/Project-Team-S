package edu.wpi.cs3733.c20.teamS.utilities;

public final class Numerics {
    private Numerics() {}

    /**
     * Clamps a value so that it is between min and max.
     * @param value The value to clamp.
     * @param min The lower-bound.
     * @param max The upper-bound.
     * @return If 'value' is between 'min' and 'max', inclusive, returns 'value'.
     * If 'value' is less than 'min', returns 'min'.
     * If 'value' is greater-than 'max', returns 'max'.
     */
    public static int clamp(int value, int min, int max) {
        if (min > max)
            throw new IllegalArgumentException("'min' can't be greater than 'max'.");

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
}
