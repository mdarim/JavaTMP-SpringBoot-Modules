package com.javatmp.demo;

import java.util.Arrays;

public class MergeSortedArrays {

    public static int[] mergeArrays(int[] arr1, int[] arr2) {
        int n = arr1.length;
        int m = arr2.length;
        int[] merged = new int[n + m];

        int i = 0, j = 0, k = 0;

        // Compare and merge
        while (i < n && j < m) {
            if (arr1[i] <= arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }

        // Copy any remaining elements from arr1
        while (i < n) {
            merged[k++] = arr1[i++];
        }

        // Copy any remaining elements from arr2
        while (j < m) {
            merged[k++] = arr2[j++];
        }

        return merged;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 1, 3, 5, 7};
        int[] arr2 = {2, 4, 6, 8};

        int[] result = mergeArrays(arr1, arr2);

        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        System.out.println(Arrays.toString(result));

    }

}
