package net.batchik.jd.concurrency.runnables;

import net.batchik.jd.concurrency.Barrier;

public class BarrierRunnable implements Runnable{
    private final int id;
    private final Barrier barrier;

    public BarrierRunnable(final int id, final Barrier barrier) {
        this.id = id;
        this.barrier = barrier;
    }

    public void run() {
        try {
            barrier.await();
            System.out.printf("[thread %02d] started...\n", id);
        } catch (final InterruptedException ex) {

        }
    }
}
