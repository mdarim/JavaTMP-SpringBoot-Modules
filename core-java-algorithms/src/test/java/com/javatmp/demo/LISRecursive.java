package com.javatmp.demo;

public class LISRecursive {
    public static int lengthOfLIS(int[] nums) {
        return helper(nums, 0, Integer.MIN_VALUE);
    }

    private static int helper(int[] nums, int index, int prev) {
        if (index == nums.length)
            return 0;
        int take = 0, skip = 0;
        if (nums[index] > prev) {
            System.out.println(prev);
            take = 1 + helper(nums, index + 1, nums[index]);
        } else {
            skip = helper(nums, index + 1, prev);
        }
        return Math.max(skip, take);
    }

    // Test the recursive LIS
    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Length of LIS: " + lengthOfLIS(nums));
    }
}
