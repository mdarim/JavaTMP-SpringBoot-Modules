package com.javatmp.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * Spring Boot Main Runner Class
 */
@SpringBootApplication
@Slf4j
public class RestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    private final RestTemplate restTemplate;

    public RestApplication(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public void run(String... args) throws Exception {
        // https://github.com/DucManhPhan/J2EE/tree/master/src/Utils
        // https://ducmanhphan.github.io/2020-02-29-How-to-use-Spring-RestTemplate-to-communicate-other-systems/
        // https://attacomsian.com/blog/http-requests-resttemplate-spring-boot
        // https://www.baeldung.com/httpclient-guide
        // https://github.com/eugenp/tutorials/tree/master/spring-resttemplate
        log.info("*** Start Spring Boot Project ***");
        log.debug("rest template instance : {}", restTemplate.toString());
        String url = "http://www.google.com/";
        String stringResponse = this.restTemplate.getForObject(url, String.class);
        log.debug("string response size : {}", stringResponse.length());

    }

}
