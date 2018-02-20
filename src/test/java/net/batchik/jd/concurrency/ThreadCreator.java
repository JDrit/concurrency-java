package net.batchik.jd.concurrency;

public interface ThreadCreator {
    Runnable create(final int id);
}
