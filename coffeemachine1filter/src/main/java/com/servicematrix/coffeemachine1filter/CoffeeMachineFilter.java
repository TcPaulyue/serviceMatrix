package com.servicematrix.coffeemachine1filter;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@MessageEndpoint
@EnableBinding(CoffeeMessageProcessor.class)
public class CoffeeMachineFilter {

    private CoffeeMessageProcessor coffeeMessageProcessor;

    public CoffeeMachineFilter(CoffeeMessageProcessor coffeeMessageProcessor){
        this.coffeeMessageProcessor = coffeeMessageProcessor;
    }
//
//    @Transformer(inputChannel = CoffeeMessageProcessor.coffeeMachineConsumer,outputChannel = CoffeeMessageProcessor.mcFilterToMc )
//    public CoffeeMessage filterMessage(Message<CoffeeMessage> coffeeMsg){
//        System.out.println("==================filter of machine1======================");
//        System.out.println("coffeeMsg: "+coffeeMsg.getPayload().id+"  "+coffeeMsg.getPayload().message);
//        CoffeeMessage coffeeMessage = coffeeMsg.getPayload();
//        return coffeeMessage;
//    }

//
//
//    @Transformer(inputChannel = CoffeeMessageProcessor.mcToMcFilter,outputChannel = CoffeeMessageProcessor.coffeeMachinePublisher)
//    public Message<CoffeeMachineMessage> filterCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMachineMsg){
//        System.out.println("==================filter of machine1======================");
//        CoffeeMachineMessage coffeeMachineMessage = coffeeMachineMsg.getPayload();
//        coffeeMachineMessage.distance = 200.00;
//        System.out.println("coffeeMachineMsg: "+coffeeMachineMsg.getPayload().coffeeMachineId+"   "+coffeeMachineMsg.getPayload().status
//                +"    "+coffeeMachineMessage.distance);
//        return coffeeMachineMsg;
//    }

    @StreamListener(CoffeeMessageProcessor.coffeeMachineConsumer)
    public void filterMessage(Message<CoffeeMessage> coffeeMsg){
        coffeeMessageProcessor.coffeeConsume().send(coffeeMsg);

    }

    @StreamListener(CoffeeMessageProcessor.mcToMcFilter)
    public void filterCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMachineMsg){
        CoffeeMachineMessage coffeeMachineMessage = coffeeMachineMsg.getPayload();
        coffeeMachineMessage.distance = 200.00;
        coffeeMessageProcessor.coffeeMachineMessageFilter().send(message(coffeeMachineMessage));
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val)
                .setHeader("destination","personConsumer")
                .build();
    }

}
