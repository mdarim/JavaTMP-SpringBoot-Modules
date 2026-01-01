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
public class JavaStreamSummaryTests {
    @Test
    void mapStreamSummaryTest() {
        List<Amount> transactions = Arrays.asList(
                new Amount("Food", 50.0, 1),
                new Amount("Transport", 20.0, 10),
                new Amount("IT", 20.0, 10),
                new Amount("Food", 30.0, 2),
                new Amount("Transport", 10.0, 1),
                new Amount("Food", 15.0, 1),
                new Amount("IT", 15.0, 1)
        );

        Map<String, Double> totalByDept = transactions.stream()
                .collect(Collectors.groupingBy(
                        Amount::getCategory,
                        Collectors.reducing(
                                (double) 0,
                                Amount::getValue,
                                Double::sum
                        )
                ));

        System.out.println(totalByDept);
        System.out.println("**********************");

        Map<String, Summary> summaryMap = transactions.stream()
                .collect(Collectors.groupingBy(
                        Amount::getCategory, // Key Mapper: Group by category name
                        Collectors.reducing( // Downstream Collector: Reduce elements in the group
                                new Summary(0.0, 0), // Identity: Starting point (initial empty Summary object)
                                amount -> new Summary(amount.getValue(), amount.getCount()), // Mapper: Transform Amount to a Summary
                                (sum1, sum2) ->
                                        new Summary(
                                                sum1.getTotalValue() + sum2.getTotalValue(),
                                                sum1.getTotalCount() + sum2.getTotalCount()) // Accumulator: Merge two Summary objects
                        )
                ));

        System.out.println(summaryMap);

    }
}
@Data
@AllArgsConstructor
class Amount {
    private String category;
    private Double value;
    private Integer count;
}
@Data
@AllArgsConstructor
class Summary {
    private Double totalValue;
    private Integer totalCount;
}