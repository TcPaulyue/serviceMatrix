package com.servicematrix.volunteerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(CoffeeMessageProcessor.class)
public class VolunteerService {
    private CoffeeMessageProcessor coffeeMessageProcessor;

    @Autowired
    public VolunteerService(CoffeeMessageProcessor coffeeMessageProcessor){
        this.coffeeMessageProcessor = coffeeMessageProcessor;
    }

    @StreamListener(value = CoffeeMessageProcessor.cfFilterToVolunteer)
    public void checkCoffeeMessage(Message<CoffeeMessage> coffeeMsg){
        CoffeeMessage coffeeMessage = coffeeMsg.getPayload();
        System.out.println(coffeeMessage.id+"  "+coffeeMessage.message);
        VolunteerMessage volunteerMessage = new VolunteerMessage();
        volunteerMessage.volunteerName="volunteer_1";
        volunteerMessage.status="free";
        volunteerMessage.distance = 100.00;
        coffeeMessageProcessor.volunteerResponse().send(message(volunteerMessage));

    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }
}
