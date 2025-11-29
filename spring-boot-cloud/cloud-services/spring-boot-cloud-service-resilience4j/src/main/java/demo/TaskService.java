package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TaskService {

    @Autowired
    TaskConnector taskConnector;

    public String getTaskDetailsCircuitBreaker(Integer id) {
        log.info("Getting task {} details using Circuit Breaker pattern", id);
        // some other business logic here
        return taskConnector.getTaskDetailsCircuitBreaker(id);
    }

    public String getTaskDetailsRetry(Integer id) {
        log.info("Getting task {} details using Retry pattern", id);
        // some other business logic here
        return taskConnector.getTaskDetailsRetry(id);
    }

    public String getTaskDetailsBulkhead(Integer id) {
        log.info("Getting task {} details using Bulkhead pattern", id);
        // some other business logic here
        return taskConnector.getTaskDetailsBulkhead(id);
    }

    public CompletableFuture<String> getTaskDetailsFutureBulkhead(Integer id) {
        log.info("Getting task {} details using Bulkhead pattern", id);
        // some other business logic here
        return taskConnector.getTaskDetailsFutureBulkhead(id);
    }

    public String getTaskDetailsRatelimiter(Integer id) {
        log.info("Getting task {} details using Ratelimiter pattern", id);
        // some other business logic here
        return taskConnector.getTaskDetailsRatelimiter(id);
    }
}
