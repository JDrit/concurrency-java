package net.batchik.jd.concurrency;

import org.junit.Test;

public class SortTests {

    @Test
    public void sortTest() {
        final int[] arr = new int[]{1,2,5,8,3,5, 2, -4, 2, 12};

        MergeSort.sort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
