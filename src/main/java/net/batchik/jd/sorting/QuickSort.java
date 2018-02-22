package net.batchik.jd.sorting;

public final class QuickSort {
    private QuickSort() { }

    public static void quickSort(final int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(final int[] arr, final int low, final int high) {
        if (low < high) {
            final int partition = partition(arr, low, high);
            sort(arr, low, partition - 1);
            sort(arr, partition + 1, high);
        }
    }

    private static int partition(final int[] arr, final int low, final int high) {
        final int pivot = arr[high];
        int i = low - 1;

        for (int j = low ; j < high ; j++) {
            if (arr[j] < pivot) {
                swap(arr, ++i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(final int[] arr, final int i1, final int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }
}
