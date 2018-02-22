package net.batchik.jd.concurrency;

import net.batchik.jd.concurrency.runnables.AllocatorRunnable;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AllocatorTests {

    private String toBinaryString(int value) {
        final StringBuilder builder = new StringBuilder();

        while (value != 0) {
            if (value == value >> 1 << 1) {
                builder.append("0");
            } else {
                builder.append("1");
            }
            value = value >> 1;
        }
        if (builder.length() == 0) {
            builder.append("0");
        }
        final char[] chars = new char[builder.length()];
        builder.getChars(0, builder.length(), chars, 0);
        for (int i = 0 ; i < chars.length / 2 ; i++) {
            char tmp = chars[i];
            chars[i] = chars[chars.length - 1 - i];
            chars[chars.length - 1 - i] = tmp;
        }
        return new String(chars);
    }

    @Test
    public void testPrinting() {
        for (int i = 0 ; i < 10 ; i++) {
            System.out.println(toBinaryString(i));
        }
    }

    @Test
    public void simpleAllocate() throws InterruptedException {
        final int size = 10;
        final IdAllocator allocator = new IdAllocator(3);
        final Barrier barrier = new Barrier(size);

        final List<Thread> threads = Utils.createThreads(size, id -> new AllocatorRunnable(id, barrier, allocator));
        Utils.run(threads);

    }
}
