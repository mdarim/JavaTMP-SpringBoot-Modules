package demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8888) // Use a random port
class Resilience4jApplicationTests {

    private static final String SERVER_ERROR_NAME = "org.springframework.web.client.HttpServerErrorException$InternalServerError";
    private static final String CIRCUIT_BREAKER_ERROR_NAME = "io.github.resilience4j.circuitbreaker.CallNotPermittedException";
    private static final String BULKHEAD_ERROR_NAME = "io.github.resilience4j.bulkhead.BulkheadFullException";
    private static final String RATELIMITER_ERROR_NAME = "io.github.resilience4j.ratelimiter.RequestNotPermitted";

    @Value("${wiremock.server.port}")
    private Integer wireMockPort;

    @LocalServerPort
    private Integer localServerPort;
    @Autowired
    private TaskService taskService;
    @MockitoSpyBean
    private RestTemplate restTemplate;

    @BeforeEach
    void initWireMock() {
        log.info("LocalServerPort: {} , WireMock port: {}", localServerPort,
                wireMockPort);
        stubFor(
                get(urlEqualTo("/task/1")).willReturn(
                        aResponse().withBody("Task 1 details"))
        );
        stubFor(
                get(urlEqualTo("/taskFuture/1")).willReturn(
                        aResponse().withBody("taskFuture 1 details"))
        );
        stubFor(
                get(urlEqualTo("/task/2")).willReturn(
                        serverError().withBody("Task 2 details failed"))
        );
    }

    @Test
    void testCircuitBreaker() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            String details = taskService.getTaskDetailsCircuitBreaker(2);
            log.info("Circuit breaker details: {}", details);
            assertThat(details).isEqualTo(
                    "Default fallback with error " + SERVER_ERROR_NAME);
        });
        IntStream.rangeClosed(1, 5).forEach(i -> {
            String details = taskService.getTaskDetailsCircuitBreaker(2);
            assertThat(details).isEqualTo(
                    "Default fallback with error " + CIRCUIT_BREAKER_ERROR_NAME);
        });
        Mockito.verify(restTemplate, Mockito.times(5))
                .getForObject("/task/{id}", String.class, 2);
    }

    @Test
    public void testRetry() {
        String result1 = taskService.getTaskDetailsRetry(1);
        log.info("result1: {}", result1);
        assertThat(result1).isEqualTo("Task 1 details");
        Mockito.verify(restTemplate, Mockito.times(1))
                .getForObject("/task/{id}", String.class, 1);

        String result2 = taskService.getTaskDetailsRetry(2);
        assertThat(result2).isEqualTo(
                "Default fallback with error " + SERVER_ERROR_NAME);
        Mockito.verify(restTemplate, Mockito.times(3))
                .getForObject("/task/{id}", String.class, 2);
    }

    @Test
    public void testBulkhead() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger failCounter = new AtomicInteger(0);

        IntStream.rangeClosed(1, 5).forEach(i -> executorService.execute(() ->
        {
            String result = taskService.getTaskDetailsBulkhead(1);
            log.info("Bulkhead result: {}", result);
            if (result.equals(
                    "Default fallback with error " + BULKHEAD_ERROR_NAME)) {
                failCounter.incrementAndGet();
            } else if (result.equals("Task 1 details")) {
                successCounter.incrementAndGet();
            }
            latch.countDown();
        }));
        latch.await();
        executorService.shutdown();

        log.info("success count: {}, fail count: {}", successCounter.get(),
                failCounter.get());
        assertThat(successCounter.get()).isEqualTo(3);
        assertThat(failCounter.get()).isEqualTo(2);
        Mockito.verify(restTemplate, Mockito.times(3))
                .getForObject("/task/{id}", String.class, 1);
    }

    @Test
    public void testRateLimiter() {
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger failCounter = new AtomicInteger(0);

        IntStream.rangeClosed(1, 10)
                .parallel()
                .forEach(i -> {
                    String result = taskService.getTaskDetailsRatelimiter(1);
                    if (result.equals("Default fallback with error " + RATELIMITER_ERROR_NAME)) {
                        failCounter.incrementAndGet();
                    } else if (result.equals("Task 1 details")) {
                        successCounter.incrementAndGet();
                    }
                });

        assertThat(successCounter.get()).isEqualTo(5);
        assertThat(failCounter.get()).isEqualTo(5);
        Mockito.verify(restTemplate, Mockito.times(5)).getForObject("/task/{id}", String.class, 1);
    }

    @Test
    public void testFutureBulkhead() throws Exception {

        CompletableFuture<String> result = taskService.getTaskDetailsFutureBulkhead(1);
        log.info("Bulkhead result: {}", result.get());
    }
}
