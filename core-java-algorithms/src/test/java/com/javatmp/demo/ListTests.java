package com.javatmp.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Slf4j
public class ListTests {
    public static List<Integer> longestIncreasingSubsequence(List<Integer> nums) {
        if (nums == null || nums.isEmpty()) return new ArrayList<>();

        int n = nums.size();
        int[] dp = new int[n];      // dp[i] = length of LIS ending at i
        int[] prev = new int[n];    // prev index for reconstruction

        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1);

        System.out.println(nums);
        System.out.println(Arrays.toString(dp));
        System.out.println(Arrays.toString(prev));

        // Compute dp values
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums.get(i) > nums.get(j) && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            System.out.print(i + " = ");
            System.out.println(Arrays.toString(dp));

        }

        // Find end of max LIS
        int maxIndex = 0;
        for (int i = 1; i < n; i++) {
            if (dp[i] > dp[maxIndex]) {
                maxIndex = i;
            }
        }

        // Reconstruct the LIS by backtracking prev[]
        List<Integer> lis = new ArrayList<>();
        for (int i = maxIndex; i != -1; i = prev[i]) {
            lis.add(nums.get(i));
        }
        Collections.reverse(lis);

        return lis;
    }

    @Test
    void longestIncreasingSubsequenceTest() {
//        List<Integer> nums = Arrays.asList(10, 22, 9, 33, 21, 50, 41, 60, 80);
        List<Integer> nums = Arrays.asList(10, 9, 2, 5, 3, 7, 101, 18);
        List<Integer> lis = longestIncreasingSubsequence(nums);
        System.out.println(lis);  // Example output: [10, 22, 33, 50, 60, 80]
    }

    public static void main(String[] args) {
            List<Integer> nums = Arrays.asList(10, 9, 2, 5, 3, 7, 101, 18);
            List<Integer> lis = longestIncreasingSubsequence(nums);
            System.out.println(lis);  // Example output: [10, 22, 33, 50, 60, 80]
    }
}

