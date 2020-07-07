package com.servicematrix.personservice;

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

    String cfFilterToVolunteer = "cfFilterToVolunteer";

    String volunteerToCfFilter = "volunteerToCfFilter";

    @Input(cfFilterToCf)
    SubscribableChannel machineConsume();


    @Output(cfToCfFilter)
    MessageChannel coffeePublish();

//    @Output(personPublisher)
//    MessageChannel coffeeFilter();
//
//    @Input(personConsumer)
//    SubscribableChannel machineFilter();





    @Output(cfFilterToVolunteer)
    MessageChannel coffeeToVolunteer();

    @Input(volunteerToCfFilter)
    SubscribableChannel volunteerToCoffee();



}
