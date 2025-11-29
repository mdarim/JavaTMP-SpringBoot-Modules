package com.javatmp.demo.cloud.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Component
public class DemoRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("*** Start DemoRunner Run ***");
    }

}
