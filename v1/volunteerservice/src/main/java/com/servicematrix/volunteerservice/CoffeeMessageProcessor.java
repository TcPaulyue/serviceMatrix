package com.servicematrix.volunteerservice;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface CoffeeMessageProcessor {


    String cfFilterToVolunteer = "cfFilterToVolunteer";

    String volunteerToCfFilter = "volunteerToCfFilter";


    @Input(cfFilterToVolunteer)
    SubscribableChannel coffeeConsume();


    @Output(volunteerToCfFilter)
    MessageChannel volunteerResponse();


}
