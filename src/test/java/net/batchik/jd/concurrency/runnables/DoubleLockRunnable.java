package net.batchik.jd.concurrency.runnables;

import net.batchik.jd.concurrency.Mutex;


/**
 * Tries to lock the mutex multiple times by the same thread.
 */
public class DoubleLockRunnable implements Runnable {
    private final int id;
    private final Mutex mutex;

    public DoubleLockRunnable(final int id, final  Mutex mutex) {
        this.id = id;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        try {
            System.out.printf("[Thread %02d] starting\n", id);

            mutex.lock();
            mutex.lock();

            System.out.printf("[Thread %02d] acquired lock\n", id);

        } finally {
            System.out.printf("[Thread %02d] releasing lock\n", id);
            mutex.unlock();
        }
    }
}
