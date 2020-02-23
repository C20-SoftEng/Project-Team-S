package edu.wpi.cs3733.c20.teamS.pathfinding;

import edu.wpi.cs3733.c20.teamS.database.NodeData;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents an immutable sequence of connected nodes that maintains the total cost
 * of traversal from start to finish.
 * This data structure is immutable: once created, an instance will never change. Operations
 * that appear to change the instance instead return a new Path instance with the change applied.
 *
 * To create a new Path instance, use the static empty() method, which takes a cost accumulator
 * function, and a starting seed value for the accumulator. If TCost is a numerical type, this
 * value should be zero.
 *
 * @apiNote It sure would have been nice to reuse the code in the ImmutablePath class. Unfortunately,
 * the Path.push() method requires an extra parameter to specify the additional cost incurred by the new
 * node. In my original C# implementation of Path, the static empty() method took a cost-calculator
 * function that computes the cost of traveling between two nodes, which allowed the push() method to
 * have a single parameter, which allowed it to implement the ImmutableStack interface.
 * I thought about doing it that way, but felt that having the Path class depend on the cost being calculated
 * from two nodes is getting outside the responsibility of the Path class, which is why I decided to have
 * the added cost be supplied to the push() method. This meant I had to write the same code twice, but as professor
 * said, inheritance is overrated, and this is an example of that.
 */
public abstract class Path implements Iterable<NodeData> {
    private Path() { }

    /**
     * Creates a new empty path that uses the specified accumulator function to add costs together, and the
     * specified starting cost.
     * @return An empty path with the specified cost accumulator function, and specified starting cost.
     * @throws IllegalArgumentException accumulator is null.
     */
    public static Path empty() {
        return new EmptyPath();
    }

    /**
     * Gets the number of nodes in the path.
     * @return The number of nodes in the path.
     */
    public abstract int size();

    /**
     * Gets the total cost of traversing the path from start to finish.
     * @return The total cost of traversing the path from start to finish.
     */
    public abstract double cost();

    /**
     * Gets the node at the head of the path without removing it.
     * @return The node at the head of the path.
     * @throws IllegalStateException The path is empty.
     */
    public abstract NodeData peek();

    /**
     * Creates a new path by pushing the specified node onto the end of the current path, with the specified additional
     * traversal cost. The current path instance is not modified.
     * @param node The node to push onto the end of the current path.
     * @param additionalCost The cost of traversing between the end of the current path and the new node.
     * @return A new path created by pushing the specified node onto the end of the current path.
     */
    public final Path push(NodeData node, double additionalCost) {
        return new NonEmptyPath(this, node, additionalCost);
    }

    /**
     * Creates a new path by popping the head node off the current path. The current path instance
     * is not modified.
     * @return A new path created by popping the head node off the current path.
     */
    public abstract Path pop();

    /**
     * Gets an iterator that iterates the current path in LIFO order.
     * @return A new iterator that iterates the current path in LIFO order.
     */
    @Override
    public final Iterator<NodeData> iterator() {
        return new Iterator<NodeData>() {
            @Override
            public boolean hasNext() {
                return !path_.isEmpty();
            }

            @Override
            public NodeData next() {
                if (!hasNext()) ThrowHelper.iteratorOffTheEnd();
                NodeData result = path_.peek();
                path_ = path_.pop();
                return result;
            }

            private Path path_ = Path.this;
        };
    }

    /**
     * Gets an unmodifiable list of nodes in the path from start to finish.
     * @return Nodes in start-to-finish order.
     */
    public final List<NodeData> startToFinish() {
        LinkedList<NodeData> result = new LinkedList<>();
        for (NodeData node : this)
            result.addFirst(node);

        return Collections.unmodifiableList(result);
    }

    /**
     * Indicates whether the current path is empty.
     * @return True if the current path is empty.
     */
    public final boolean isEmpty() {
        return size() == 0;
    }

    private static final class EmptyPath extends Path {
        public EmptyPath() {
            super();
        }

        @Override
        public int size() {
            return 0;
        }
        @Override
        public double cost() {
            return 0;
        }
        @Override
        public NodeData peek() {
            throw new IllegalStateException("Can't peek an empty path.");
        }
        @Override
        public Path pop() {
            throw new IllegalStateException("Can't pop an empty path.");
        }
    }

    private static final class NonEmptyPath extends Path {
        private final NodeData head_;
        private final Path tail_;
        private final double totalCost_;
        private final int size_;

        public NonEmptyPath(Path tail, NodeData head, double additionalCost) {
            super();
            this.head_ = head;
            this.tail_ = tail;
            this.size_ = tail.size() + 1;
            this.totalCost_ = tail.cost() + additionalCost;
        }

        @Override
        public int size() {
            return size_;
        }
        @Override
        public double cost() {
            return totalCost_;
        }
        @Override
        public NodeData peek() {
            return head_;
        }
        @Override
        public Path pop() {
            return tail_;
        }

    }
}
