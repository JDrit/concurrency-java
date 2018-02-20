package net.batchik.jd.concurrency;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class ReadWriteLock {
    // the number of threads waiting to write
    private int waitingWrites = 0;

    // list of all threads that are currently reading, to allow reentrant behavior
    @Nonnull private Set<Thread> readingThreads = new HashSet<>();

    // the thread that has the single write lock
    @Nullable private Thread writingThread;

    public synchronized void readLock() throws InterruptedException {
        final Thread currentThread = Thread.currentThread();

        if (readingThreads.contains(currentThread)) {
            return;
        }

        while (writingThread != null || waitingWrites != 0) {
            /*
             * Some other thread is either already writing or there is a thread that is waiting to write.
             * This will give priority to write operations over read operations in order to prevent starvation.
             */
            wait();
        }

        readingThreads.add(currentThread);
    }

    public synchronized void readUnlock() {
        readingThreads.remove(Thread.currentThread());
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        final Thread currentThread = Thread.currentThread();

        if (writingThread == currentThread) {
            return;
        }

        waitingWrites++;
        while (writingThread != null || !readingThreads.isEmpty()) {
            wait();
        }
        waitingWrites--;
        writingThread = currentThread;
    }

    public synchronized void writeUnlock() {
        writingThread = null;
        notifyAll();
    }
}
