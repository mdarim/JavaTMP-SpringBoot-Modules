package demo;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TaskConnector {
    @Autowired
    RestTemplate restTemplate;

    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "getTaskDetailsFallback")
    public String getTaskDetailsCircuitBreaker(Integer id) {
        log.info("getTaskDetailsCircuitBreaker");
        return restTemplate.getForObject("/task/{id}", String.class, id);
    }

    @Retry(name = "RetryService", fallbackMethod = "getTaskDetailsFallback")
    public String getTaskDetailsRetry(Integer id) {
        log.info("getTaskDetailsRetry id : {}", id);
        return restTemplate.getForObject("/task/{id}", String.class, id);
    }

    @Bulkhead(name = "BulkheadService",/*type = Bulkhead.Type.THREADPOOL,*/ fallbackMethod = "getTaskDetailsFallback")
    public String getTaskDetailsBulkhead(Integer id) {
        log.info("getTaskDetailsBulkhead id : {}", id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return restTemplate.getForObject("/task/{id}", String.class, id);
    }

    @RateLimiter(name = "RateLimiterService", fallbackMethod = "getTaskDetailsFallback")
    public String getTaskDetailsRatelimiter(Integer id) {
        return restTemplate.getForObject("/task/{id}", String.class, id);
    }

    public String getTaskDetailsFallback(Integer id, Throwable error) {
        return "Default fallback with error " + error.getClass().getName();
    }

    @Bulkhead(name = "FutureBulkheadService", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "fallback")
    public CompletableFuture<String> getTaskDetailsFutureBulkhead(Integer id) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getTaskDetailsBulkhead id : {}", id);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return restTemplate.getForObject("/taskFuture/{id}", String.class, id);
        });
    }
    private CompletableFuture<String> fallback(Integer id, BulkheadFullException ex) {
        return CompletableFuture.completedFuture("Service is currently busy. Please try again later.");
    }


}
