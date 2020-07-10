package com.servicematrix.personproxy;

import com.servicematrix.personproxy.domain.PersonInfo;
import com.servicematrix.personproxy.domain.PersonLocation;
import net.sf.cglib.proxy.Enhancer;
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
