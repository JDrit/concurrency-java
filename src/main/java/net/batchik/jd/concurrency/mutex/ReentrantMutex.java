package net.batchik.jd.concurrency.mutex;

public class ReentrantMutex implements Mutex {

    private boolean isLocked = false;
    private Thread lockOwner;

    @Override
    public synchronized void lock() {
        while (isLocked && lockOwner != Thread.currentThread()) {
            try {
                wait();
            } catch (final InterruptedException ex) { }
        }
        isLocked = true;
        lockOwner = Thread.currentThread();
    }

    @Override
    public synchronized boolean tryLock() {
        if (isLocked) {
            if (lockOwner == Thread.currentThread()) {
                return true;
            } else {
                return false;
            }
        } else {
            isLocked = true;
            lockOwner = Thread.currentThread();
            return true;
        }
    }


    @Override
    public synchronized void unlock() {
        isLocked = false;
        lockOwner = null;
        notifyAll();
    }

    @Override
    public synchronized boolean isLocked() {
        return isLocked;
    }
}
