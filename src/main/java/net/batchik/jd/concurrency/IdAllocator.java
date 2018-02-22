package net.batchik.jd.concurrency;

import java.util.ArrayDeque;
import java.util.OptionalInt;
import java.util.Queue;

public class IdAllocator {
    private final Queue<Integer> queue;
    private int maxUsed = 0;
    private final int max;

    /**
     * Constructs an allocation system that will not let any more than a configurable amount of IDs from (0, n] out
     * at any-one time.
     * @param max the max IDs to give out
     */
    public IdAllocator(final int max) {
        this.queue = new ArrayDeque<>();
        this.max = max;
    }

    /**
     * Checks to see if there are any IDs open and if so, returns one. This is non-blocking and will return
     * back an {@link OptionalInt#empty()} if there is nothing available.
     */
    public synchronized OptionalInt tryAllocate() {
        if (!queue.isEmpty()) {
            return OptionalInt.of(queue.poll());
        } else if (maxUsed != max) {
            return OptionalInt.of(maxUsed++);
        } else {
            return OptionalInt.empty();
        }
    }

    /**
     * Tries to allocate an ID. This will block will an ID is open.
     * @return the new ID to use
     * @throws InterruptedException if an interrupt was raised during the wait call
     */
    public synchronized int allocate() throws InterruptedException {
        while (queue.isEmpty() && maxUsed == max) {
            wait();
        }

        if (queue.isEmpty()) {
            return maxUsed++;
        } else {
            return queue.poll();
        }
    }

    /**
     * Marks the ID as returned. The client can no longer make use of this ID after this method is called.
     * @param id the ID to return
     */
    public synchronized void release(final int id) {
        queue.add(id);
        notify();
    }
}
