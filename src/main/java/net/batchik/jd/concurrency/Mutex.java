package net.batchik.jd.concurrency;

public interface Mutex {

    /**
     * Locks the mutex, potentially blocking the current thread.
     */
    void lock();

    /**
     * Tries to grab the lock on the mutex.
     * @return true if the lock was grabbed, false if the lock belongs to another thread
     */
    boolean tryLock();

    /**
     * Gives the lock back to the mutex.
     */
    void unlock();

    /**
     * Determines the current state of the mutex
     * @return true if the mutex has been locked by some thread, false otherwise.
     */
    boolean isLocked();
}
