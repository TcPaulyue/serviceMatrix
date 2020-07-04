package com.servicematrix.messagecenter;


import com.servicematrix.messagecenter.channel.MessageChannelInput;
import com.servicematrix.messagecenter.channel.MessageChannelOutput;
import com.servicematrix.messagecenter.domain.CoffeeMachineMessage;
import com.servicematrix.messagecenter.domain.CoffeeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.servicematrix.messagecenter.MessagecenterApplication.resourceStateList;

@Service
@EnableBinding({MessageChannelInput.class, MessageChannelOutput.class})
public class MessageRouter {

    private MessageChannelInput messageChannelInput;

    private MessageChannelOutput messageChannelOutput;

    Double maxDistance = 10000.00;

    @Autowired
    public MessageRouter(MessageChannelInput messageChannelInput,MessageChannelOutput messageChannelOutput){
        this.messageChannelInput = messageChannelInput;
        this.messageChannelOutput = messageChannelOutput;
    }

    @StreamListener(MessageChannelInput.personPublisher)
    public void checkPersonMessage(Message<CoffeeMessage> coffeeMsg){
        String destination = Objects.requireNonNull(coffeeMsg.getHeaders().get("destination")).toString();
        if(destination.equals(MessageChannelOutput.coffeeMachineConsumer)){
        //    this.coffeeMachineScheduler(coffeeMsg.getPayload());
            messageChannelOutput.sendCoffeeMachineMessage().send(coffeeMsg);
        }
    }

    @StreamListener(MessageChannelInput.coffeeMachinePublisher)
    public void checkCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMachineMsg){
        String destination = Objects.requireNonNull(coffeeMachineMsg.getHeaders().get("destination")).toString();
        System.out.println(destination);
        switch (destination) {
            case MessageChannelOutput.personConsumer:
                messageChannelOutput.sendPersonMessage().send(coffeeMachineMsg);
                break;
            case "messageCenter":
                CoffeeMachineMessage coffeeMachineMessage = coffeeMachineMsg.getPayload();
                if (coffeeMachineMessage.coffeeMachineId.equals("coffeeMachine_1"))
                    resourceStateList.put("coffeeMachine_1", coffeeMachineMessage);
                else if (coffeeMachineMessage.coffeeMachineId.equals("coffeeMachine_2"))
                    resourceStateList.put("coffeeMachine_2", coffeeMachineMessage);
                break;
            case "kettle":
                System.out.println("heat water");
                break;
        }
    }

    public String coffeeMachineScheduler(CoffeeMessage coffeeMessage){
        Double distance = maxDistance;
        AtomicReference<String> coffeeMachineId = null;
        resourceStateList.forEach((key,coffeeMachineMessage)->{
            if(key.contains("coffeeMachine")){
                if(coffeeMachineMessage.distance<distance&&coffeeMachineMessage.status.equals("idleState"))
                    coffeeMachineId.set(key);

            }
        });
        return coffeeMachineId.get();
    }


}
