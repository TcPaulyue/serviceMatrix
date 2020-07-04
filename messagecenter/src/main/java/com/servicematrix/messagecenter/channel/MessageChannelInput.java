package com.servicematrix.messagecenter.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageChannelInput {

    String personPublisher = "personPublisher";

    String coffeeMachinePublisher = "coffeeMachinePublisher";


    @Input(personPublisher)
    SubscribableChannel subscribePersonMessage();


    @Input(coffeeMachinePublisher)
    SubscribableChannel subscribeCoffeeMachineMessage();


}
