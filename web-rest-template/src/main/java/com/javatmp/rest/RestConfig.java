package com.javatmp.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.restclient.RestTemplateBuilder;


import java.time.Duration;

@Configuration
@Slf4j
public class RestConfig {
    @Bean("RestTemplateExplicit")
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(restTemplate -> {
            // customize the rest template bean here
            log.debug("an instance of Rest Template created");
        });
    }

    @Bean
    public RestTemplate restTemplate(
            @Qualifier("RestTemplateExplicit") RestTemplateBuilder builder) {
        return builder
                .connectTimeout(Duration.ofMillis(3000))
                .readTimeout(Duration.ofMillis(3000))
                .build();
    }

}
