package net.batchik.jd.concurrency;

import com.google.common.base.MoreObjects;

public class Semaphore {
    private final int maxSize;
    private int currentSize = 0;

    public Semaphore(final int maxSize) {
        this.maxSize = maxSize;
    }

    public void acquire() throws InterruptedException {
        acquire(1);
    }

    public synchronized void acquire(final int amount) throws InterruptedException {
        while (currentSize + amount > maxSize) {
            wait();
        }
        currentSize += amount;
    }

    /**
     * Releases a single permit from the semaphore
     */
    public void release() {
        release(1);
    }

    /**
     * Releases a bunch of permits from the semaphore
     * @param amount the amount of permits to let go of
     */
    public synchronized void release(final int amount) {
        currentSize -= amount;
        notifyAll();
    }

    /**
     * Atomically gets the number of permits that are available to hand out.
     */
    public synchronized int getFreePermits() {
        return maxSize - currentSize;
    }

    @Override
    public synchronized String toString() {
        return MoreObjects.toStringHelper(this)
                .add("maxPermits", maxSize)
                .add("currentPermits", currentSize)
                .toString();
    }
}
