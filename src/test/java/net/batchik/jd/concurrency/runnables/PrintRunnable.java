package net.batchik.jd.concurrency.runnables;

import net.batchik.jd.concurrency.Semaphore;

import java.util.concurrent.TimeUnit;

public class PrintRunnable implements Runnable {
    private static final long sleepTime = TimeUnit.SECONDS.toMillis(1);
    private final int id;
    private final Semaphore semaphore;

    public PrintRunnable(final int id, final  Semaphore semaphore) {
        this.id = id;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            System.out.printf("[Thread %02d] starting\n", id);
            semaphore.acquire();

            System.out.printf("[Thread %02d] acquired lock\n", id);
            Thread.sleep(sleepTime);

        } catch (final InterruptedException ex) {

        } finally {
            System.out.printf("[Thread %02d] releasing lock\n", id);
            semaphore.release();
        }
    }
}
