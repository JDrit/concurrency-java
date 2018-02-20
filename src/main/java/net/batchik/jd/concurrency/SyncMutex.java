package net.batchik.jd.concurrency;

public class SyncMutex implements Mutex {
    private boolean isLocked = false;

    @Override
    public synchronized void lock() {
        while (isLocked) {
            try {
                wait();
            } catch (final InterruptedException ex) {
            }
        }
        isLocked = true;
    }

    @Override
    public synchronized boolean tryLock() {
        if (isLocked) {
            return false;
        } else {
            isLocked = true;
            return  true;
        }
    }

    @Override
    public synchronized void unlock() {
        isLocked = false;
        notify();
    }

    @Override
    public synchronized boolean isLocked() {
        return isLocked;
    }
}
