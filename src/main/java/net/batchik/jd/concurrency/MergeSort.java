package net.batchik.jd.concurrency;

public class MergeSort {

    public static void sort(final int[] arr) {
        final int[] helper = new int[arr.length];
        sort(arr, helper, 0, arr.length - 1);
    }

    private static void sort(final int[] input, final int[] helper, final int low, final int high) {
        if (low < high) {
            final int middle = low + (high - low) / 2;

            sort(input, helper, low, middle);
            sort(input, helper, middle + 1, high);
            merge(input, helper, low, middle, high);
        }
    }

    private static void merge(int[] arr, final int[] helper, final int low, final int middle, final int high) {

        //System.arraycopy(arr, low, helper, low, high + 1 - low);
        for (int i = low ; i <= high ; i++) {
            helper[i] = arr[i];
        }

        int resultLeft = low;
        int resultRight = middle + 1;
        int current = low;

        while (resultLeft <= middle && resultRight <= high) {
            if (helper[resultLeft] <= helper[resultRight]) {
                arr[current] = helper[resultLeft];
                current++;
                resultLeft++;
            } else {
                arr[current] = helper[resultRight];
                current++;
                resultRight++;
            }
        }

        while (resultLeft <= middle) {
            arr[current++] = helper[resultLeft++];
        }


    }
}
