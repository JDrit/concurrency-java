package net.batchik.jd.sorting;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class SortTests {

    private static void print(int[] arr) {
        final StringBuilder builder = new StringBuilder("{");
        for (int i = 0 ; i < arr.length - 1 ; i++) {
            builder.append(arr[i]).append(", ");
        }
        builder.append(arr[arr.length - 1]).append("}");
        System.out.println(builder.toString());
    }

    private static int[] randomArr(final int size) {
        final int[] arr = new int[size];
        for (int i = 0 ; i < size ; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(0, 1024);
        }
        return arr;
    }

    private static void validate(final int[] arr) {
        int prev = Integer.MIN_VALUE;

        for (int i : arr) {
            if (i < prev) {
                Assert.fail("number " + i + " is less than previus of " + prev);
            }
            prev = i;
        }

    }

    @Test
    public void quickSortTest() {
        final int size = 15;
        final int[] arr = randomArr(size);
        print(arr);

        QuickSort.quickSort(arr);

        print(arr);
        validate(arr);
    }

    @Test
    public void mergeTest() {
        final int size = 10;
        final int[] arr = randomArr(size);
        print(arr);

        final int[] sorted = MergeSort.mergeSort(arr);
        print(sorted);
        validate(sorted);
    }
}
