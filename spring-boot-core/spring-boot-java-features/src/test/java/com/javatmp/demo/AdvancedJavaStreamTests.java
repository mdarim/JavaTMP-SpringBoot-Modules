package com.javatmp.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class AdvancedJavaStreamTests {
    @Test
    void mapStreamTest() {
        List<Product> products = Arrays.asList(
                new Product("Electronics", 499.99, "Laptop"),
                new Product("Books", 19.99, "Java Guide"),
                new Product("Electronics", 99.50, "Mouse"),
                new Product("Books", 45.00, "Advanced Java"),
                new Product("Books", 30 , "Java Tutorial"));
        Map<String, List<String>> namesByCategory = products.stream()
                .collect(
                        Collectors.groupingBy(
                                Product::getCategory,
                                Collectors.mapping(Product::getName, Collectors.toList()) // Map to names and collect in a List

                        )
                );

        System.out.println(namesByCategory);
    }

    @Test
    void convertListToMapTest() {
        /*
        To convert a List of objects into a Map of objects using Java streams, you primarily
         use the Collectors.toMap() collector. This requires you to specify two key functions:
         one to define the key of the map and another to define the value of the map.
         */
        List<Person> people = Arrays.asList(
                new Person(101, "Alice"),
                new Person(102, "Bob"),
                new Person(103, "Charlie"));

        // Example 1: Mapping by a Unique Key (ID to Person Object)
        Map<Integer, Person> peopleMap = people.stream()
                .collect(Collectors.toMap(
                        Person::getId,       // Key Extractor: Use the ID as the map key
                        person -> person     // Value Extractor: Use the entire Person object as the value
                ));
        System.out.println(peopleMap);

        // Alternatively, using the Function.identity() utility for the value mapper:
        Map<Integer, Person> peopleMapIdentity = people.stream()
                .collect(Collectors.toMap(
                        Person::getId,
                        Function.identity() // Equivalent to person -> person
                ));
        System.out.println(peopleMapIdentity);
    }

    @Test
    void convertListToMapWithDuplicateKeyTest() {
        List<Person> peopleWithDuplicates = Arrays.asList(
                new Person(101, "Alice"),
                new Person(102, "Bob"),
                new Person(101, "AliceDuplicate") // Duplicate ID!
        );
        Map<Integer, String> namesMap = peopleWithDuplicates.stream()
                .collect(Collectors.toMap(
                        Person::getId,      // Key: ID
                        Person::getName,    // Value: Name
                        (oldValue, newValue) -> oldValue // Merge Function: Keep the existing (old) value if a duplicate key is found
                ));

        System.out.println(namesMap);

        Map<Integer, Person> orderedPeopleMap = peopleWithDuplicates.stream()
                .collect(Collectors.toMap(
                        Person::getId,
                        Function.identity(),
                        (oldValue, newValue) -> oldValue, // Merge function (even if keys are unique, it's needed for the 4-arg method)
                        LinkedHashMap::new                // Map Supplier: Use a LinkedHashMap implementation
                ));
        System.out.println(namesMap);

    }
}

@Data
@AllArgsConstructor
class Product {
    private String category;
    private double price;
    private String name;
}

@Data
@AllArgsConstructor
class Person {
    private int id;
    private String name;
}