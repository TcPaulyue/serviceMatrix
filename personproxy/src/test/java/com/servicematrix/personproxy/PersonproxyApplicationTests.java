package com.servicematrix.personproxy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonproxyApplicationTests {

    @Autowired
    CoffeeMessagePublisher coffeeMessagePublisher;

    @Test
    void contextLoads() {
    }

}
