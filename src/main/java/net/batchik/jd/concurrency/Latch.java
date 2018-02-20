package net.batchik.jd.concurrency;

public class Latch {
    private int size;

    public Latch(final int size) {
        this.size = size;
    }

    public synchronized void countdown() {
        if (size-- <= 0) {
            notifyAll();
        }
    }

    public synchronized void await() throws InterruptedException {
        while (size > 0) {
            wait();
        }
    }
}
