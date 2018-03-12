package net.batchik.jd.concurrency.runnables;

import net.batchik.jd.concurrency.ReentrantReadWriteLock;

import java.util.concurrent.ThreadLocalRandom;

public class WriterRunnable implements Runnable {
    private final int id;
    private final ReentrantReadWriteLock lock;

    public WriterRunnable(final int id, final ReentrantReadWriteLock lock) {
        this.id = id;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 400));

            lock.writeLock();
            System.out.printf("[thread %02d] took write lock\n", id);
            Thread.sleep(ThreadLocalRandom.current().nextInt(50, 200));

            lock.writeUnlock();
            System.out.printf("[thread %02d] released write lock\n", id);
        } catch (final InterruptedException ex) {

        }
    }
}
