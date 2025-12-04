package com.javatmp.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class Java15Tests {

    @Test
    void TextBlock() {
        String textBlock = """
            Hello, World!
            This is a multi-line text block.
            Welcome to Java 15.
            """;
        System.out.println(textBlock);
        log.debug(textBlock);

        String sqlQuery = """
            SELECT id, name, email
            FROM users
            WHERE status = 'active'
            ORDER BY name;
            """;
        System.out.println(sqlQuery);
        log.debug(sqlQuery);

        String html = """
            <html>
                <body>
                    <h1>Welcome to Java 15</h1>
                </body>
            </html>
            """;
        System.out.println(html);

        String textBlock1 = """
            [
            Hello\tWorld!\nJava 15 is here!
            ]
            """;
        System.out.println(textBlock1);

        String json = """
            {
                "name": "John Doe",
                "age": 30,
                "active": true
            }
            """;
        System.out.println(json);

    }

    @Test
    void textBlockConcatExample () {
        String name = "Java 15";
        String message = """
            Welcome to %s!
            """.formatted(name);

        System.out.println(message);

        String textBlock = """
            He said, "Hello, World!"
            'Single quotes' are also fine.
            """;
        System.out.println(textBlock);

        String name1 = "John";
        int age = 30;

        String textBlock2 = """
            Name: %s
            Age: %d
            """.formatted(name1, age);

        System.out.println(textBlock2);

    }

}
