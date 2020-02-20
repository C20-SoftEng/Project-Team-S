package edu.wpi.cs3733.c20.teamS.pathfinding;

import edu.wpi.cs3733.c20.teamS.ThrowHelper;

import java.util.Iterator;

/**
 * An immutable Last In First Out (LIFO) data structure. Instances of this class never change.
 * Operations that would mutate a normal stack instead create an entirely new stack with the
 * change applied. For example, push() doesn't change the current stack, it creates an entirely
 * new stack that is the same as the current one, except that the specified item is added to the top.
 * Likewise, pop() doesn't change the current stack, it returns an entirely new stack that is the
 * same as the current one, except that the top item has been removed.
 *
 * Use the static empty() method to get an empty stack.
 * @param <T> The type of item.
 */
public abstract class ImmutableStack<T>  {
    private ImmutableStack() {}

    /**
     * Gets a new empty stack.
     * @param <U> Type of items in the stack.
     * @return a new immutable stack with no items in it.
     */
    public static <U> ImmutableStack<U> empty() {
        return new EmptyStack<>();
    }

    /**
     * Gets the number of items in the stack.
     * @return The number of items in the stack.
     */
    public abstract int size();

    /**
     * Gets the item at the top of the stack without removing it.
     * @return The item at the top of the stack.
     * @throws IllegalStateException The current stack is empty.
     */
    public abstract T peek();

    /**
     * Produces a new stack, the same as the current one, but with the specified item pushed
     * onto it. The current stack instance is not modified.
     * @param item The item to push.
     * @return A new stack, produced by pushing an item onto the current stack.
     */
    public final ImmutableStack<T> push(T item) {
        return new NonEmptyStack<>(item, this);
    }

    /**
     * Produces a new stack, the same as the current one, but with the top item removed.
     * The current stack is not modified.
     * @return A new stack that is the same as the current one, except that the top item has been removed.
     * @throws IllegalStateException The current stack is empty.
     */
    public abstract ImmutableStack<T> pop();

    public final boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Gets an iterator that iterates the current stack in LIFO order.
     * @return
     */
    public final Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                if (!hasNext()) ThrowHelper.iteratorOffTheEnd();

                T result = stack.peek();
                stack = stack.pop();
                return result;
            }

            private ImmutableStack<T> stack = ImmutableStack.this;
        };
    }

    private static final class EmptyStack<T> extends ImmutableStack<T> {
        public EmptyStack() {
            super();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public T peek() {
            throw new IllegalStateException("Can't peek an empty stack.");
        }

        @Override
        public ImmutableStack<T> pop() {
            throw new IllegalStateException("Can't pop an empty stack.");
        }
    }

    private static final class NonEmptyStack<T> extends ImmutableStack<T> {
        public NonEmptyStack(T head, ImmutableStack<T> tail) {
            this.head = head;
            this.tail = tail;
            this.size_ = tail.size() + 1;
        }

        @Override
        public int size() {
            return size_;
        }

        @Override
        public T peek() {
            return head;
        }

        @Override
        public ImmutableStack<T> pop() {
            return tail;
        }

        private final T head;
        private final ImmutableStack<T> tail;
        private final int size_;
    }
}

