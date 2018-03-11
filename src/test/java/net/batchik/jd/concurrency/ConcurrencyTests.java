package net.batchik.jd.concurrency;

import net.batchik.jd.concurrency.runnables.BarrierRunnable;
import net.batchik.jd.concurrency.runnables.CountdownRunnable;
import net.batchik.jd.concurrency.runnables.DoubleLockRunnable;
import net.batchik.jd.concurrency.runnables.MutexRunnable;
import net.batchik.jd.concurrency.runnables.PrintRunnable;
import net.batchik.jd.concurrency.runnables.WriterRunnable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTests {

    @Rule
    public Timeout globalTimeout= new Timeout(25, TimeUnit.SECONDS);

    private static List<Thread> createThreads(final int size, final ThreadCreator creator) {
        final List<Thread> threads = new ArrayList<>(size);
        for (int i = 0 ; i < size ; i++) {
            threads.add(new Thread(creator.create(i)));
        }
        return threads;
    }

    private static void run(final List<Thread> threads) throws InterruptedException {
        for (final Thread thread : threads) {
            thread.start();
        }
        for (final Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    public void singleWriterTest() throws InterruptedException {
        final int size = 10;
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final List<Thread> threads = createThreads(size, id -> new WriterRunnable(id, lock));
        run(threads);
    }

    @Test
    public void countdownTest() throws InterruptedException {
        final int size = 10;
        final Latch latch = new Latch(size);
        final List<Thread> threads = createThreads(size, id -> new CountdownRunnable(id, latch));
        run(threads);
        latch.await();
        System.out.println("[main] finished");
    }

    @Test
    public void barrierTest() throws InterruptedException {
        final int size = 10;
        final Barrier barrier = new Barrier(10);
        final List<Thread> threads = createThreads(size, id -> new BarrierRunnable(id, barrier));
        run(threads);
    }

    @Test
    public void reentrantLockTest() throws InterruptedException {
        final int size = 10;
        final Mutex mutex = new ReentrantMutex();
        final List<Thread> threads = createThreads(size, id -> new MutexRunnable(id, mutex));
        run(threads);
    }

    @Test
    public void fifoMutexTest() throws InterruptedException {
        final int size = 10;
        final Mutex mutex = new FifoMutex();
        final List<Thread> threads = createThreads(size, id -> new MutexRunnable(id, mutex));
        run(threads);
    }

    @Test
    public void syncMutexTest() throws InterruptedException {
        final int size = 10;
        final Mutex mutex = new SyncMutex();
        final List<Thread> threads = createThreads(size, id -> new MutexRunnable(id, mutex));
        run(threads);
    }

    @Test
    public void doubleLockTest() throws InterruptedException {
        final int size = 10;
        final Mutex mutex = new SyncMutex();
        final List<Thread> threads = createThreads(size, id -> new DoubleLockRunnable(id, mutex));
        run(threads);
    }

    @Test
    public void semaphoreTests() throws InterruptedException {
        final int size = 10;
        final int locks = 2;
        final Semaphore semaphore = new Semaphore(locks);
        final List<Thread> threads = createThreads(size, id -> new PrintRunnable(id, semaphore));
        run(threads);
    }
}
