package com.javatmp.demo;
import java.util.ArrayList;
import java.util.List;

public class LongestContiguousIncreasing {

    public static List<Integer> longestContiguousIncreasing(int[] arr) {
        int n = arr.length;
        if (n == 0) return new ArrayList<>();

        int start = 0;
        int maxLen = 1;
        int maxStart = 0;

        // Loop through the array and find longest contiguous increasing segment
        for (int i = 1; i < n; i++) {
            System.out.format("i = %d, arr[i] = %d, start = %d, maxLen = %d, maxStart = %d\n",
                    i, arr[i], start, maxLen, maxStart);
            if (arr[i] <= arr[i - 1]) {
                int currentLen = i - start;
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                    maxStart = start;
                }
                start = i;
            }
        }

        // Check last segment
        if (n - start > maxLen) {
            maxLen = n - start;
            maxStart = start;
        }

        // Build result subsequence
        List<Integer> result = new ArrayList<>();
        for (int i = maxStart; i < maxStart + maxLen; i++) {
            result.add(arr[i]);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {4, 0 ,1, 5, 6, 3, 0, 7, 8, 9, 1, 2};
        List<Integer> longest = longestContiguousIncreasing(arr);
        System.out.println("Longest contiguous increasing subsequence: " + longest);
    }
}