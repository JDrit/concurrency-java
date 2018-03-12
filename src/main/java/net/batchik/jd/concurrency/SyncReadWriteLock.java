package net.batchik.jd.concurrency;

public class SyncReadWriteLock {

    private int readCount;
    private int writeCount;
    private int waitingWrites;

    public synchronized void readLock() throws InterruptedException {
        while (writeCount > 0 || waitingWrites > 0) {
            wait();
        }
        readCount++;
    }

    public synchronized void readUnlock() {
        readCount--;
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        waitingWrites++;

        while (readCount > 0 || writeCount > 0) {
            wait();
        }

        waitingWrites--;
        writeCount++;
    }

    public synchronized void writeUnlock() {
        writeCount--;
        notifyAll();
    }
}
