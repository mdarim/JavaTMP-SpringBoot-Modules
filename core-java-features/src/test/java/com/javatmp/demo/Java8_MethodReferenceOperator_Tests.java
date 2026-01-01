package com.javatmp.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootTest
@Slf4j
public class Java8_MethodReferenceOperator_Tests {

    public static <T> T mergeThings(T a, T b, BiFunction<T, T, T> merger) {
        return merger.apply(a, b);
    }

    public static String appendStrings(String a, String b) {
        return a + b;
    }

    public String appendStrings2(String a, String b) {
        return a + b;
    }

    @Test
    void BiFunction() {

        Java8_MethodReferenceOperator_Tests myApp = new Java8_MethodReferenceOperator_Tests();

        // Calling the method mergeThings with a lambda expression
        System.out.println(Java8_MethodReferenceOperator_Tests.
                mergeThings("Hello ", "World!", (a, b) -> a + b));

        // Reference to a static method
        System.out.println(Java8_MethodReferenceOperator_Tests.
                mergeThings("Hello ", "World!", Java8_MethodReferenceOperator_Tests::appendStrings));

        // Reference to an instance method of a particular object
        System.out.println(Java8_MethodReferenceOperator_Tests.
                mergeThings("Hello ", "World!", myApp::appendStrings2));

        // Reference to an instance method of an arbitrary object of a
        // particular type
        System.out.println(Java8_MethodReferenceOperator_Tests.
                mergeThings("Hello ", "World!", String::concat));

    }

    @Test
    void StaticMethodReference() {
        // Using a static method reference
        BiFunction<Integer, Integer, Integer> maxFunction = Math::max;

        // Call the method
        int result = maxFunction.apply(10, 20);
        System.out.println("Max value: " + result);
    }

    @Test
    void InstanceMethodReferenceExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        // Using instance method reference
        names.forEach(System.out::println);

        List<Integer> numbers = Arrays.asList(5, 3, 50, 24, 40, 2, 9, 18);
        numbers.stream()
                .sorted((a, b) -> a.compareTo(b))
                .forEach(System.out::println);

        System.out.println("*** using references");
        numbers.stream()
                .sorted(Integer::compareTo)
                .forEach(System.out::println);


    }

    @Test
    void ArbitraryInstanceMethodReferenceExample() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        // Using instance method reference of an arbitrary object
        names.sort(String::compareToIgnoreCase);

        // Print sorted names
        names.forEach(System.out::println);
    }

    @Test
    void ConstructorReferenceExample() {
        // Using a constructor reference
        Supplier<ArrayList<String>> arrayListSupplier = ArrayList::new;

        // Create a new ArrayList
        ArrayList<String> list = arrayListSupplier.get();
        list.add("Java");
        list.add("Python");

        System.out.println(list);
    }

    @Test
    void fun() {
        Function<Integer, String> intToString = String::valueOf;

        System.out.println(intToString.apply(10));

    }
}
