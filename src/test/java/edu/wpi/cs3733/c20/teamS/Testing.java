package edu.wpi.cs3733.c20.teamS;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public final class Testing {
    private Testing() {}

    public static <T> void assertSetEquals(Collection<T> expected, Collection<T> actual) {
        assertEquals(
                new HashSet<>(expected),
                new HashSet<>(actual)
        );
    }
}
