package com.servicematrix.personservice;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CoffeeMessagePublisher {

//    String COFFEECHANNEL1 = "personToCoffeeMc";
//    String COFFEECHANNEL2 = "coffeeMCToPerson";
//
//    @Output(COFFEECHANNEL1)
//    MessageChannel coffeePublish();
//
//    @Input(COFFEECHANNEL2)
//    SubscribableChannel coffeeMachineMessageConsumer();


    String cfToCfFilter = "cfToCfFilter";

    String cfFilterToMcFilter = "cfFilterToMcFilter";

    String mcFilterToCfFilter = "mcFilterToCfFilter";

    String cfFilterToCf = "cfFilterToCf";

    @Output(cfToCfFilter)
    MessageChannel coffeePublish();

    @Output(cfFilterToMcFilter)
    MessageChannel coffeeFilter();

    @Input(mcFilterToCfFilter)
    SubscribableChannel machineFilter();

    @Input(cfFilterToCf)
    SubscribableChannel machineConsume();





}
