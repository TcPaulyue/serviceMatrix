package com.servicematrix.coffeemachine1service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(CoffeeMessageProcessor.class)
public class CoffeeMachineService {
    private CoffeeMessageProcessor coffeeMessageProcessor;

    @Autowired
    public CoffeeMachineService(CoffeeMessageProcessor coffeeMessageProcessor){
        this.coffeeMessageProcessor = coffeeMessageProcessor;
    }

    @StreamListener(value = CoffeeMessageProcessor.mcFilterToMc)
    public void checkCoffeeMessage(Message<CoffeeMessage> coffeeMsg){
        CoffeeMessage coffeeMessage = coffeeMsg.getPayload();
        System.out.println(coffeeMessage.id+"  "+coffeeMessage.message);

        CoffeeMachineMessage coffeeMachineMessage = new CoffeeMachineMessage();
        coffeeMachineMessage.coffeeMachineId="coffeeMachine_1";
        coffeeMachineMessage.status="idleState";
        coffeeMessageProcessor.coffeeMachineMessageProducer().send(message(coffeeMachineMessage));

    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }
}
