package com.servicematrix.coffeemachine1filter;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface CoffeeMessageProcessor {

    String coffeeMachineConsumer = "coffeeMachineConsumer";

    String coffeeMachinePublisher = "coffeeMachinePublisher";

    String mcFilterToMc = "mcFilterToMc1";

    String mcToMcFilter = "mc1ToMcFilter";

    @Input(coffeeMachineConsumer)
    SubscribableChannel coffeeFilter();

    @Output(coffeeMachinePublisher)
    MessageChannel coffeeMachineMessageFilter();


    @Output(mcFilterToMc)
    MessageChannel coffeeConsume();

    @Input(mcToMcFilter)
    SubscribableChannel coffeeMachineMessageProducer();


}
