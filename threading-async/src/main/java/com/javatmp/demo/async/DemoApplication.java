package com.javatmp.demo.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.stream.IntStream;

/**
 * Spring Boot Main Runner Class
 */
@SpringBootApplication
@Slf4j
@EnableAsync
public class DemoApplication {

    @Autowired
    TaskFactory taskFactory;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner springBootMain() throws Exception {
        return args -> {
            log.info("*** Start Spring Boot Project ***");
            IntStream.rangeClosed(1, 5).forEach(i -> {
                try {
                    this.taskFactory.doAsyncTask("task " + i, i * i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        };
    }

}
