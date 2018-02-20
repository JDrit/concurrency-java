package net.batchik.jd.concurrency;

public class Barrier {
    private int size;

    public Barrier(final int size) {
        this.size = size;
    }

    public synchronized int getSize() {
        return size;
    }

    public synchronized void await() throws InterruptedException {
        size--;
        if (size <= 0) {
            notifyAll();
        } else {
            while (size > 0) {
                wait();
            }
        }
    }
}
