package edu.wpi.cs3733.c20.teamS.utilities;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * You've got a sequence of fence posts, but you want the boards between them. A board
 * consists of two fence posts.
 */
public final class Board<TPost> {
    private final TPost start;
    private final TPost end;

    private Board() {
        this(null, null);
    }
    private Board(TPost start, TPost end) {
        this.start = start;
        this.end = end;
    }

    public TPost start() {
        return start;
    }
    public TPost end() {
        return end;
    }
    public boolean isEmpty() {
        return start == null && end == null;
    }
    public boolean isComplete() {
        return start != null && end != null;
    }

    private Board<TPost> next(TPost nextPost) {
        if (start == null)
            return new Board<>(nextPost, null);
        if (end == null)
            return new Board<>(start, nextPost);
        return new Board<>(end, nextPost);
    }

    /**
     * Creates an empty board (i.e. a board where both posts are null).
     * @param <TPost> Type of post in the board.
     * @return A board where both posts are null.
     */
    public static <TPost> Board<TPost> empty() {
        return new Board<>();
    }
    public static <TPost> Board<TPost> create(TPost start, TPost end) {
        if (start == null) ThrowHelper.illegalNull("start");
        if (end == null) ThrowHelper.illegalNull("end");

        return new Board<>(start, end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board<?> board = (Board<?>) o;
        return Objects.equals(start, board.start) &&
                Objects.equals(end, board.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }

    private static class BoardIterator<TPost> implements Iterator<Board<TPost>> {
        private final Iterator<TPost> inner;
        private Board<TPost> current;

        public BoardIterator(Iterator<TPost> inner) {
            this.inner = inner;
            current = empty();
            if (inner.hasNext())
                current = current.next(inner.next());
        }

        @Override
        public boolean hasNext() {
            return inner.hasNext();
        }
        @Override
        public Board<TPost> next() {
            current = current.next(inner.next());
            return current;
        }
    }

    /**
     * Creates a lazily-evaluated sequence of boards from a sequence of posts.
     * @param posts A sequence of posts.
     * @param <TPost> A sequence containing all the boards between the posts.
     * @return A sequence of all the boards between the posts.
     */
    public static <TPost> Iterable<Board<TPost>> asIterable(Iterable<TPost> posts) {
        if (posts == null) ThrowHelper.illegalNull("posts");

        return () -> new BoardIterator<>(posts.iterator());
    }

    /**
     * Creates an unmodifiable list of boards from the specified sequence of posts.
     * @param posts Sequence of posts.
     * @param <TPost> Type of post
     * @return A list of all the boards between the posts in the specified sequence.
     */
    public static <TPost> List<Board<TPost>> asList(Iterable<TPost> posts) {
        Iterable<Board<TPost>> iterable = asIterable(posts);
        List<Board<TPost>> result = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        return Collections.unmodifiableList(result);
    }
}
