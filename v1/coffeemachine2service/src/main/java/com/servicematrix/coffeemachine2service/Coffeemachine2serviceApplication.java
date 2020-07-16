package com.servicematrix.coffeemachine2service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
public class Coffeemachine2serviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Coffeemachine2serviceApplication.class, args);
    }

}
