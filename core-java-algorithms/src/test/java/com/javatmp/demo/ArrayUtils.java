package com.javatmp.demo;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ArrayUtils {
    public static void main(String[] args) {
        Integer[] arr = { 3, 5, 77, 12, 43, 22 };

        System.out.println(Arrays.toString(arr));

        Arrays.sort(arr, (a, b) -> (int) (0.5 - Math.random()));

        System.out.println(Arrays.toString(arr));
    }
}
