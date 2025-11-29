package com.javatmp.demo.cloud.resilience4j;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ExternalService {

    public String callServiceThrowUnCheckedException() {
        log.info("callService");
        throw new RuntimeException("RuntimeException from ExternalService");
    }

    public String callServiceThrowCheckedException() throws Exception {
        log.info("callService");
        throw new Exception("Exception from ExternalService");
    }

    public String fallbackMethod(Throwable t) {
        log.info("fallbackMethod");
        return "Fallback response";
    }
}
