package com.servicematrix.coffeemachine2service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

//@Service
//@EnableBinding(CoffeeMessageProcessor.class)
//public class CoffeeMachineFilter {
//
//    @Transformer(inputChannel = CoffeeMessageProcessor.coffeeMachineConsumer,outputChannel = CoffeeMessageProcessor.mcFilterToMc )
//    public CoffeeMessage filterMessage(Message<CoffeeMessage> coffeeMsg){
//        System.out.println("==================filter of machine2======================");
//        System.out.println("coffeeMsg: "+coffeeMsg.getPayload().id+"  "+coffeeMsg.getPayload().message);
//        CoffeeMessage coffeeMessage = coffeeMsg.getPayload();
//        return coffeeMessage;
//    }
//
//    @Transformer(inputChannel = CoffeeMessageProcessor.mcToMcFilter,outputChannel = CoffeeMessageProcessor.coffeeMachinePublisher)
//    public CoffeeMachineMessage filterCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMachineMsg){
//        System.out.println("==================filter of machine2======================");
//        CoffeeMachineMessage coffeeMachineMessage = coffeeMachineMsg.getPayload();
//        coffeeMachineMessage.distance = 100.00;
//        System.out.println("coffeeMachineMsg: "+coffeeMachineMsg.getPayload().coffeeMachineId+"   "+coffeeMachineMsg.getPayload().status
//        +"   "+coffeeMachineMessage.distance);
//        return coffeeMachineMessage;
//    }
//}