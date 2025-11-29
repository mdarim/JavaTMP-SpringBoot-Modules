package com.javatmp.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class DemoApplicationTests {

//    @Test
    @Disabled
    void contextLoads1() {
        log.debug("*** SpringBoot Test Context Load 1 ***");
    }

//    @Test
    @Disabled
    void contextLoads2() {
        log.debug("*** SpringBoot Test Context Load 2 ***");
    }

}
