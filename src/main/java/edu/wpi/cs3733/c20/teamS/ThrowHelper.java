package edu.wpi.cs3733.c20.teamS;

/**
 * Contains methods for throwing exceptions.
 */
public abstract class ThrowHelper {
    private ThrowHelper() {}

    /**
     * Throws an IllegalArgumentException with a message that the argument can't be null.
     * @param argumentName The name of the argument that was null.
     */
    public static void illegalNull(String argumentName) {
        throw new IllegalArgumentException("'" + argumentName + "' can't be null.");
    }

    /**
     * Throws an IllegalArgumentException for an argument that was outside the acceptable range.
     * @param argumentName The name of the argument.
     * @param lowerBound The lowest legal value.
     * @param upperBound The highest legal value.
     */
    public static void outOfRange(String argumentName, Object lowerBound, Object upperBound) {
        throw new IllegalArgumentException(
                "'" + argumentName + "' must be between <" +
                        lowerBound + "> and <" + upperBound + ">.");
    }

    /**
     * Throws an IllegalStateException for an iterator that was advanced past the end.
     */
    public static void iteratorOffTheEnd() {
        throw new IllegalStateException("Attempted to advance iterator past the end.");
    }
}
