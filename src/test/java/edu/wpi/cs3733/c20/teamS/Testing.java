package edu.wpi.cs3733.c20.teamS;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains helper functions that are incredibly useful for unit testing. These are functions I've ended up writing
 * multiple times over due to usually making them private member functions of the test class.
 */
public final class Testing {
    private Testing() {}

    /**
     * Uses Set equality semantics to check whether two collections are equal. Set-equality checks whether
     * two collections contain all the same items. Order does not matter, and duplicates don't matter.
     * Even if you already have two sets in hand you should consider calling this method. The reason is because
     * not all of Java's Set classes implement equality to apply to any set. For example, some of the sets
     * returned by methods such as Collections.emptySet() and Collections.singleton() will compare 'not equal'
     * to a HashSet that has the same elements (i.e. emptySet() will compare 'not equal' to an empty HashSet). This
     * has bitten me multiple times when writing JUnit tests. This method avoids the issue. You should use it.
     * @param expected The expected items.
     * @param actual The actual items.
     * @param <T> The type of item in the set.
     */
    public static <T> void assertSetEquals(Collection<T> expected, Collection<T> actual) {
        assertEquals(
                new HashSet<>(expected),
                new HashSet<>(actual)
        );
    }
}
