package net.batchik.jd.heap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class ArrayHeap<T extends Comparable<T>> implements Heap<T> {
    private final ArrayList<T> arr;

    public ArrayHeap() {
        this(10);
    }

    public ArrayHeap(final int size) {
        arr = new ArrayList<>(size);
    }

    @Override
    public void push(@Nonnull final T elem) {
        arr.add(elem);
        shuffleUp(arr.size() - 1);
    }

    @Nullable
    @Override
    public T pop() {
        if (arr.size() == 0) {
            return null;
        } else if (arr.size() == 1) {
            final T result = arr.get(0);
            arr.remove(0);
            return result;
        } else {
            final T result = arr.get(0);
            final T newHead = arr.get(arr.size() - 1);
            arr.remove(arr.size() - 1);
            arr.set(0, newHead);
            shuffleDown(0);
            return result;
        }
    }

    private int parent(final int index) {
        return (index - 1) / 2;
    }

    private int left(final int index) {
        return index * 2 + 1;
    }

    private int right(final int index) {
        return index * 2 + 2;
    }

    private void swap(final int a, final int b) {
        final T tmp = arr.get(a);
        arr.set(a, arr.get(b));
        arr.set(b, tmp);
    }

    private void shuffleDown(final int index) {
        final int leftI = left(index);
        final int rightI = right(index);

        if (leftI < arr.size()) {
            int minIndex = leftI;

            if (rightI < arr.size() && arr.get(rightI).compareTo(arr.get(leftI)) < 0) {
                minIndex = rightI;
            }

            if (arr.get(minIndex).compareTo(arr.get(index)) < 0) {
                swap(minIndex, index);
                shuffleDown(minIndex);
            }
        }
    }

    private void shuffleUp(int index) {
        if (index > 0) {
            final int parentI = parent(index);
            if (arr.get(index).compareTo(arr.get(parentI)) < 0) {
                swap(index, parentI);
                shuffleUp(parentI);
            }
        }
    }

    @Nullable
    @Override
    public T peek() {
        if (arr.size() == 0) {
            return null;
        } else {
            return arr.get(0);
        }
    }

    @Override
    public int size() {
        return arr.size();
    }

}
