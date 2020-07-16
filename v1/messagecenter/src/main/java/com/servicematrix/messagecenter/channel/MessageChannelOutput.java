package com.servicematrix.messagecenter.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageChannelOutput {


    String coffeeMachineConsumer = "coffeeMachineConsumer";
    String personConsumer = "personConsumer";


    @Output(coffeeMachineConsumer)
    MessageChannel sendCoffeeMachineMessage();


    @Output(personConsumer)
    MessageChannel sendPersonMessage();

}
