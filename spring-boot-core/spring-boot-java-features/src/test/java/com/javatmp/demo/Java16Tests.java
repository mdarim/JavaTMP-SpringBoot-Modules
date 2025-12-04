package com.javatmp.demo;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

@SpringBootTest
@Slf4j
public class Java16Tests {
    @Test
    void instanceofTest() {
        Object obj = "Hello, Java 16!";
        Object obj2 = new BigInteger("12");
        if (obj instanceof String str) {
            System.out.println("String length: " + str.length());
        }
        if (obj2 instanceof String str) {
            System.out.println("obj2 length: " + str.length());
        } else {
            System.out.println("obj2 class name: " + obj2.getClass().getName());
        }
    }

    @Test
    void LogicalOperatorExample () {
        Object obj = "Hello, Java!";

        if (obj instanceof String str && str.length() > 5) {
            System.out.println("String is long enough: " + str);
        }
    }
}
