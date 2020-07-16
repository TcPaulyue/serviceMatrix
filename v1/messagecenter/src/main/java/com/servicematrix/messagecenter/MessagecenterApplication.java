package com.servicematrix.messagecenter;

import com.servicematrix.messagecenter.domain.CoffeeMachineMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@SpringBootApplication
public class MessagecenterApplication {


    public final static HashMap<String, CoffeeMachineMessage> resourceStateList = new HashMap<String, CoffeeMachineMessage>();


    @PostConstruct
    public void setResourceStateList(){
        resourceStateList.put("coffeeMachine_1",new CoffeeMachineMessage());
        resourceStateList.put("coffeeMachine_2",new CoffeeMachineMessage());
    }

    public static void main(String[] args) {
        SpringApplication.run(MessagecenterApplication.class, args);
    }

}
