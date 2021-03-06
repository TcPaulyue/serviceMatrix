package com.servicematrix.coffeemachine1service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface CoffeeMessageProcessor {


//    String coffeeMachineConsumer = "coffeeMachineConsumer";
//
//    String coffeeMachinePublisher = "coffeeMachinePublisher";


    String mcFilterToMc = "mcFilterToMc1";

    String mcToMcFilter = "mc1ToMcFilter";

//    @Input(coffeeMachineConsumer)
//    SubscribableChannel coffeeFilter();
//
//
//    @Output(coffeeMachinePublisher)
//    MessageChannel coffeeMachineMessageFilter();


    @Input(mcFilterToMc)
    SubscribableChannel coffeeConsume();

    @Output(mcToMcFilter)
    MessageChannel coffeeMachineMessageProducer();



}
