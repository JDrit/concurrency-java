package net.batchik.jd.concurrency;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class FifoMutex implements Mutex {

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        public boolean isHeldExclusively() {
            return getState() == 1;
        }

        @Override
        protected boolean tryAcquire(final int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected boolean tryRelease(final int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public boolean isLocked() {
        return sync.isHeldExclusively();
    }
}
