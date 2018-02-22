package net.batchik.jd.concurrency;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStack<T> {
    private final AtomicReference<Node<T>> top = new AtomicReference<>();

    /**
     * Adds an item to the top of the stack.
     * @param value the new data to put in
     * @return the number of items that are now in the stack
     */
    public int push(@Nullable final T value) {
        Node<T> currentHead;
        Node<T> newHead;

        do {
            currentHead = top.get();
            final int newSize = (currentHead == null) ? 0 : currentHead.size + 1;
            newHead = new Node<>(value, newSize, currentHead);
        } while (!top.compareAndSet(currentHead, newHead));
        return newHead.size;
    }

    /**
     * Gets the value that is on the top of the stack.
     */
    @Nonnull
    public Optional<T> pop() {
        Node<T> currentHead;
        Node<T> newHead;

        do {
            currentHead = top.get();
            if (currentHead == null) {
                return Optional.empty();
            } else {
                newHead = currentHead.nextNode;
            }
        } while (!top.compareAndSet(currentHead, newHead));
        return Optional.of(currentHead.value);
    }

    /**
     * Gets the current size of the stack.
     */
    public int getSize() {
        final Node<T> node = top.get();
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    private static class Node<T> {
        private final T value;
        private final int size;
        private final Node<T> nextNode;

        Node(final T value, final int size, final Node<T> next) {
            this.value = value;
            this.size = size;
            this.nextNode = next;
        }
    }
}
