package com.javatmp.demo.cloud.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Slf4j
@Component
public class CircuitBreakerDemoRunner implements CommandLineRunner {
    @Autowired
    ExternalService externalService;

    @Override
    public void run(String... args) throws Exception {
        log.info("*** Start Spring Boot Project ***");
        CircuitBreakerConfig cbc = CircuitBreakerConfig.ofDefaults();
        CircuitBreakerRegistry cbcRegistry = CircuitBreakerRegistry.of(cbc);
        CircuitBreaker circuitBreaker = cbcRegistry
                .circuitBreaker("generalCircuitBreaker", cbc);
        circuitBreaker
                .getEventPublisher()
                .onError(event -> log.error("CircuitBreaker error event: {}", event))
                .onStateTransition(event -> log.info("CircuitBreaker state transition event: {}", event));

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowSize(1)
                .build();
        CircuitBreakerRegistry customRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker customBreaker = customRegistry
                .circuitBreaker("customCircuitBreaker", circuitBreakerConfig);

        IntStream.rangeClosed(1, 2).forEach(value -> {
            try {
                log.info("Circuit breaker value {}", value);
                Supplier<String> supplier = CircuitBreaker.decorateSupplier(
                        customBreaker,
                        externalService::callServiceThrowUnCheckedException);
                log.info("result from supplier: " + supplier.get());
            } catch (Exception r) {
                log.error("Exception thrown by supplier: " + r.getMessage());
            }
        });

    }

}
