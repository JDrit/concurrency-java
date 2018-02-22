package net.batchik.jd.sorting;

public class MergeSort {


    public static int[] mergeSort(final int[] arr) {
        if (arr.length > 1) {
            final int middle = arr.length / 2;
            int[] left = new int[middle];
            int[] right = new int[arr.length - middle];

            System.arraycopy(arr, 0, left, 0, left.length);
            System.arraycopy(arr, middle, right, 0, right.length);

            System.out.println(right[right.length - 1]);

            left = mergeSort(left);
            right = mergeSort(right);

            return merge(left, right);

        } else {
            return arr;
        }
    }

    private static int[] merge(final int[] left, final int[] right) {
        final int[] result = new int[left.length + right.length];
        int resultIndex = 0;
        int leftIndex = 0;
        int rightIndex = 0;


        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] < right[rightIndex]) {
                result[resultIndex++] = left[leftIndex++];
            } else {
                result[resultIndex++] = right[rightIndex++];
            }
        }

        while (leftIndex < left.length) {
            result[resultIndex++] = left[leftIndex++];
        }

        while (rightIndex < right.length) {
            result[resultIndex++] = right[rightIndex++];
        }

        return result;
    }


}
