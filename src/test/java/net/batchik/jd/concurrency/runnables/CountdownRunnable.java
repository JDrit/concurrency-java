package net.batchik.jd.concurrency.runnables;

import net.batchik.jd.concurrency.Latch;

import java.util.concurrent.ThreadLocalRandom;

public class CountdownRunnable implements Runnable {
    private final int id;
    private final Latch latch;

    public CountdownRunnable(final int id, final Latch latch) {
        this.id = id;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            final long sleep = ThreadLocalRandom.current().nextInt(200, 1000);
            Thread.sleep(sleep);
            latch.countdown();
            System.out.printf("[Thread %02d] finishing...\n", id);

        } catch (final InterruptedException ex) {
        }
    }
}
