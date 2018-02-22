package net.batchik.jd.count;

import java.util.concurrent.TimeUnit;

public class HitCount {
    private final long retention;
    private final TimeUnit timeUnit;
    private Node head;
    private Node tail;
    private long currentCount;

    public HitCount(final long retention, final TimeUnit timeUnit) {
        this.retention = retention;
        this.timeUnit = timeUnit;
    }

    public void recordHit() {
        final long seconds = System.currentTimeMillis() / 1000;
        resize();
        currentCount++;
        if (head == null) {
            final Node node = new Node(seconds);
            head = node;
            tail = node;
        } else if (tail.timestamp == seconds) {
            tail.count++;
        } else {
            final Node node = new Node(seconds);
            tail.next = node;
            tail = node;
        }
    }

    public long getCount() {
        resize();
        return currentCount;
    }

    private void resize() {
        final long validTime = System.currentTimeMillis() - timeUnit.toMillis(retention);

        while (head != null && head.timestamp < validTime) {
            currentCount -= head.count;
            head = head.next;
        }
        if (head == null) {
            tail = null;
        }
    }

    static class Node {
        private final long timestamp;
        private long count;
        private Node next;

        Node(final long timestamp) {
            this.timestamp = timestamp;
            this.count = 1;
        }
    }
}
