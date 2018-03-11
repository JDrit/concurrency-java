package net.batchik.jd.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockBasedRwLock {
    private final Lock globalLock = new ReentrantLock();
    private final Condition condition = globalLock.newCondition();

    private int readCount;
    private int writeCount;
    private int waitingWrites;

    public void readLock() throws InterruptedException {
        try {
            globalLock.lock();
            while (writeCount > 0 || waitingWrites > 0) {
                condition.await();
            }
            readCount++;
        } finally {
            globalLock.unlock();
        }
    }

    public void readUnlock() {
       try {
           globalLock.lock();
           readCount--;
           if (readCount == 0) {
               condition.signalAll();
           }
       } finally {
           globalLock.unlock();
       }
    }

    public void writeLock() throws InterruptedException {
        try {
            globalLock.lock();
            waitingWrites++;

            while (readCount > 0 || writeCount > 0) {
                condition.await();
            }

            waitingWrites--;
            writeCount++;

        } finally {
            globalLock.unlock();
        }
    }

    public void writeUnlock() {
        try {
            globalLock.lock();
            writeCount--;
            condition.notifyAll();
        } finally {
            globalLock.unlock();
        }
    }
}
