package com.servicematrix.personfilter;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;


@Component
public interface CoffeeMessagePublisher {
    String cfToCfFilter = "cfToCfFilter";

    String cfFilterToCf = "cfFilterToCf";




    String personPublisher = "personPublisher";

    String personConsumer = "personConsumer";

//    String cfFilterToVolunteer = "cfFilterToVolunteer";
//
//    String volunteerToCfFilter = "volunteerToCfFilter";

    @Output(cfFilterToCf)
    MessageChannel machineConsume();


    @Input(cfToCfFilter)
    SubscribableChannel coffeePublish();

    @Output(personPublisher)
    MessageChannel coffeeFilter();

    @Input(personConsumer)
    SubscribableChannel machineFilter();

}
