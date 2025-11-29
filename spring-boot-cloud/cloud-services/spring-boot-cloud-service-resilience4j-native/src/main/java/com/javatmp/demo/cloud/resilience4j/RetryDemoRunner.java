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
public class RetryDemoRunner implements CommandLineRunner {
    @Autowired
    ExternalService externalService;
    @Override
    public void run(String... args) throws Exception {
        log.info("*** Start Spring Boot Project ***");
        RetryConfig config = RetryConfig.ofDefaults(); // ----> 1
        RetryRegistry registry = RetryRegistry.of(config); // ----> 2
        Retry retry = registry.retry("generalRetry", config); // ----> 3

        Supplier<String> supplier = Retry.decorateSupplier(retry, externalService::callServiceThrowUnCheckedException); // ----> 5
        try {
            log.info("result from supplier: " + supplier.get());
        } catch (RuntimeException r) {
            log.error("Exception thrown by supplier: " + r.getMessage());
        }

        RetryConfig configSearch = RetryConfig.<String>custom()
                .maxAttempts(3)
                .waitDuration(Duration.of(3, SECONDS))
                .retryOnResult(error ->
                        error.contains("small error")
                ).build();

        log.info("Try to call on error contains");
        Retry retryOnSmall = registry.retry("errorRetry", configSearch);
        supplier = Retry.decorateSupplier(retryOnSmall, externalService::callServiceThrowUnCheckedException); // ----> 5
        try {
            log.info("result from supplier: " + supplier.get());
        } catch (RuntimeException r) {
            log.error("Exception thrown by supplier: " + r.getMessage());
        }
    }

}
