package net.batchik.jd.heap;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HeapTests {

    @Test
    public void addTest() {
        final ArrayHeap<Integer> heap = new ArrayHeap<>();

        heap.push(1);

        assertEquals(1, heap.size());
        assertEquals((Integer) 1, heap.peek());
        assertEquals((Integer) 1, heap.pop());
        assertEquals(0, heap.size());
        assertEquals(null, heap.peek());
        assertEquals(null, heap.pop());
    }

    @Test
    public void emptyHeap() {
        final ArrayHeap<Integer> heap = new ArrayHeap<>();
        assertEquals(0, heap.size());
        assertEquals(null, heap.pop());
        assertEquals(null, heap.pop());
        assertEquals(null, heap.peek());
    }

    @Test
    public void addSeveral() {
        final ArrayHeap<Integer> heap = new ArrayHeap<>();

        heap.push(5);
        heap.push(4);

        assertEquals((Integer) 4, heap.peek());

        heap.push(2);

        assertEquals((Integer) 2, heap.pop());
        assertEquals((Integer) 4, heap.pop());

        heap.push(100);
        assertEquals((Integer) 5, heap.pop());
        assertEquals((Integer) 100, heap.pop());
    }

    @Test
    public void addForwards() {
        final ArrayHeap<Integer> heap = new ArrayHeap<>();

        for (int i = 0 ; i < 100 ; i++) {
            heap.push(i);
        }
        for (int i = 0 ; i < 100 ; i++) {
            assertEquals((Integer) i, heap.pop());
        }
        assertEquals(null, heap.pop());
    }

    @Test
    public void addBackwards() {
        final int size = 400;
        final ArrayHeap<Integer> heap = new ArrayHeap<>();

        for (int i = size ; i > 0 ; i--) {
            heap.push(i);
        }

        for (int i = 1 ; i <= size ; i++) {
            assertEquals((Integer) i, heap.pop());
        }
        assertEquals(null, heap.pop());
    }

    @Test
    public void insertRandom() {
        final List<Integer> input = new ArrayList<>(100);
        final ArrayHeap<Integer> heap = new ArrayHeap<>(100);
        final int size = 100;
        for (int i = 0 ; i < size ; i++) {
            input.add(i);
        }
        Collections.shuffle(input);

        for (int i : input) {
            heap.push(i);
        }

        for (int i = 0 ; i < size ; i++) {
            assertEquals((Integer) i, heap.pop());
        }

    }
}
