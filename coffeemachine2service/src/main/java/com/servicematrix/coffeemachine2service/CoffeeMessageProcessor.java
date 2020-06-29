package com.servicematrix.coffeemachine2service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface CoffeeMessageProcessor {

//    String COFFEECHANNEL_IN = "personToCoffeeMc";
//
//    String PERSONCHANNEL = "coffeeMCToPerson";
//

//    @Input(COFFEECHANNEL_IN)
//    SubscribableChannel coffeeMessageConsumer();
//
//
//    @Output(PERSONCHANNEL)
//    MessageChannel coffeeMachineMessageProducer();

    String cfFilterToMcFilter = "cfFilterToMcFilter";

    String mcFilterToCfFilter = "mcFilterToCfFilter";

    String mcFilterToMc = "mcFilterToMc2";

    String mcToMcFilter = "mc2ToMcFilter";

    @Input(cfFilterToMcFilter)
    SubscribableChannel coffeeFilter();

    @Input(mcFilterToMc)
    SubscribableChannel coffeeConsume();

    @Output(mcToMcFilter)
    MessageChannel coffeeMachineMessageProducer();

    @Output(mcFilterToCfFilter)
    MessageChannel coffeeMachineMessageFilter();

}
