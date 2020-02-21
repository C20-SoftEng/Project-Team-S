package edu.wpi.cs3733.c20.teamS.utilities;

import edu.wpi.cs3733.c20.teamS.Testing;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static edu.wpi.cs3733.c20.teamS.Testing.assertSequenceEquals;
import static edu.wpi.cs3733.c20.teamS.Testing.assertSetEquals;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {
    public static class EmptyBoard extends BoardTests {
        private final Board<String> empty = Board.empty();

        @Test
        public void hasNullStart() {
            assertNull(empty.start());
        }
        @Test
        public void hasNullEnd() {
            assertNull(empty.end());
        }
        @Test
        public void isNotComplete() {
            assertFalse(empty.isComplete());
        }
        @Test
        public void isEmpty() {
            assertTrue(empty.isEmpty());
        }
    }

    public static class NonEmptyBoard extends BoardTests {
        private final String start = "start";
        private final String end = "end";
        private final Board<String> board = Board.create(start, end);

        @Test
        public void isComplete() {
            assertTrue(board.isComplete());
        }

        @Test
        public void isNotEmpty() {
            assertFalse(board.isEmpty());
        }

        @Test
        public void hasSpecifiedStart() {
            assertEquals(start, board.start());
        }

        @Test
        public void hasSpecifiedEnd() {
            assertEquals(end, board.end());
        }
    }

    public static class EmptyPostSequence {
        private final List<String> posts = Collections.emptyList();

        @Test
        public void asList_yieldsEmptyList() {
            List<Board<String>> actual = Board.asList(Collections.emptyList());

            assertSequenceEquals(Collections.emptyList(), actual);
        }
    }

    public static class SinglePost {
        private final String item = "single";
        private final List<String> posts = Collections.singletonList(item);

        @Test
        public void asList_yieldsEmpty() {
            List<Board<String>> actual = Board.asList(posts);

            assertSequenceEquals(Collections.emptyList(), actual);
        }
    }

    public static class TwoPosts {
        private final String start = "start";
        private final String end = "end";
        private final List<String> posts = Arrays.asList(start, end);

        @Test
        public void asList_yieldsSingleton() {
            List<Board<String>> expected = Collections.singletonList(Board.create(start, end));
            List<Board<String>> actual = Board.asList(posts);

            assertSequenceEquals(expected, actual);
        }
    }

    public static class ManyPosts {
        private final List<String> posts = Arrays.asList(
                "start", "mid1", "mid2", "mid3", "end"
        );

        @Test
        public void asList_yieldsCorrectNumberOfBoards() {
            List<Board<String>> expected = Arrays.asList(
                    Board.create("start", "mid1"),
                    Board.create("mid1", "mid2"),
                    Board.create("mid2", "mid3"),
                    Board.create("mid3", "end")
            );
            List<Board<String>> actual = Board.asList(posts);

            assertSequenceEquals(expected, actual);
        }
    }
}
