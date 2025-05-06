package com.shann.bookmyshow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Profile("test")
@TestPropertySource("classpath:application-test.properties")
class BookMyShowApplicationTests {

    @Test
    void contextLoads() {
    }

}
