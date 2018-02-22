package net.batchik.jd.concurrency.runnables;

import net.batchik.jd.concurrency.Barrier;
import net.batchik.jd.concurrency.IdAllocator;

import java.util.concurrent.TimeUnit;

public class AllocatorRunnable implements Runnable {
    private final int id;
    private final Barrier barrier;
    private final IdAllocator allocator;

    public AllocatorRunnable(final int id, final Barrier barrier, final IdAllocator allocator) {
        this.id = id;
        this.barrier = barrier;
        this.allocator = allocator;
    }

    @Override
    public void run() {
        int allocation = 0;
        try {
            barrier.await();

            allocation = allocator.allocate();
            System.out.printf("[thread %02d] got id %d\n", id, allocation);
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));

        } catch (final InterruptedException ex) {

        } finally {
            allocator.release(allocation);
        }

    }
}
