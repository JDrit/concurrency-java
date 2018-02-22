package net.batchik.jd.concurrency;

import java.util.ArrayList;
import java.util.List;

public final class Utils {

    private Utils() { }

    static List<Thread> createThreads(final int size, final ThreadCreator creator) {
        final List<Thread> threads = new ArrayList<>(size);
        for (int i = 0 ; i < size ; i++) {
            threads.add(new Thread(creator.create(i)));
        }
        return threads;
    }

    static void run(final List<Thread> threads) throws InterruptedException {
        for (final Thread thread : threads) {
            thread.start();
        }
        for (final Thread thread : threads) {
            thread.join();
        }
    }
}
