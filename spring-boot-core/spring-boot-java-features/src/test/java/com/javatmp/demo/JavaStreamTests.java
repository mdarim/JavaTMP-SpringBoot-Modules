package com.javatmp.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
public class JavaStreamTests {
    @Test
    void mapStreamTest() {
        List<String> stringNumbers = Arrays.asList("1", "2", "3", "4", "5");
        List<Integer> numbers = stringNumbers.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        System.out.println(numbers); // Output: [1, 2, 3, 4, 5]
    }

    @Test
    void flatMapStreamTest() {

        List<Integer> nums = Stream.of(
                        List.of(1, 2, 3, 4),
                        List.of(10, 11, 23, 8),
                        List.of(4, 7, 9, 1))
                .flatMap(Collection::stream)
                .toList();
        Integer max = nums.stream().max(Integer::compareTo).get();

        System.out.println("max is = " + max);

    }

    @Test
    void removeDuplicatesTest() {
        List<String> stringNumbers = Arrays.asList("1", "2", "2", "4", "5", "4",
                "5", "3");
        List<String> unique = stringNumbers.stream()
                .collect(Collectors.toMap(String::valueOf, Function.identity(),
                        (o1, o2) -> o1))
                .values().stream()
                .toList();

        System.out.println(unique); // Output: [1, 2, 3, 4, 5]

    }

}
