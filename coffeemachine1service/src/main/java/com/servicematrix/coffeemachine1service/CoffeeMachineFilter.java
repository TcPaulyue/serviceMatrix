package com.servicematrix.coffeemachine1service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@EnableBinding(CoffeeMessageProcessor.class)
public class CoffeeMachineFilter {

    @Transformer(inputChannel = CoffeeMessageProcessor.cfFilterToMcFilter,outputChannel = CoffeeMessageProcessor.mcFilterToMc )
    public CoffeeMessage filterMessage(Message<CoffeeMessage> coffeeMsg){
        System.out.println("222222222"+coffeeMsg.getPayload().id+"  "+coffeeMsg.getPayload().message);
        CoffeeMessage coffeeMessage = coffeeMsg.getPayload();
        return coffeeMessage;
    }

    @Transformer(inputChannel = CoffeeMessageProcessor.mcToMcFilter,outputChannel = CoffeeMessageProcessor.mcFilterToCfFilter)
    public CoffeeMachineMessage filterCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMachineMsg){
        System.out.println("coffeeMachineMsg: "+coffeeMachineMsg.getPayload().coffeeMachineId+"   "+coffeeMachineMsg.getPayload().status);
        CoffeeMachineMessage coffeeMachineMessage = coffeeMachineMsg.getPayload();
        coffeeMachineMessage.distance = 100.00;
        return coffeeMachineMessage;
    }
}
