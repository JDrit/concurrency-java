package net.batchik.jd.concurrency.runnables;

import net.batchik.jd.concurrency.Mutex;

import java.util.concurrent.TimeUnit;

/**
 * Runnable that will try and lock twice on the same mutex.
 */
public class MutexRunnable implements Runnable {
    private static final long sleepTime = TimeUnit.SECONDS.toMillis(1);
    private final int id;
    private final Mutex mutex;

    public MutexRunnable(final int id, final  Mutex mutex) {
        this.id = id;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        try {
            System.out.printf("[Thread %02d] starting\n", id);

            mutex.lock();

            System.out.printf("[Thread %02d] acquired lock\n", id);

            Thread.sleep(sleepTime);

        } catch (final InterruptedException ex) {
        } finally {
            System.out.printf("[Thread %02d] releasing lock\n", id);
            mutex.unlock();
        }
    }
}
