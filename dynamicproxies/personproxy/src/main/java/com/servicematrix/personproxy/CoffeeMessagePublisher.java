package com.servicematrix.personproxy;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CoffeeMessagePublisher {


    String cfToCfFilter = "cfToCfFilter";

    String cfFilterToCf = "cfFilterToCf";



//
//    String personPublisher = "personPublisher";
//
//    String personConsumer = "personConsumer";

    @Input(cfFilterToCf)
    SubscribableChannel machineConsume();


    @Output(cfToCfFilter)
    MessageChannel coffeePublish();

//    @Output(personPublisher)
//    MessageChannel coffeeFilter();
//
//    @Input(personConsumer)
//    SubscribableChannel machineFilter();

}
