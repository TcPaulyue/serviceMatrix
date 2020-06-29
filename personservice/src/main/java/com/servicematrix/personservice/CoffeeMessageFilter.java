package com.servicematrix.personservice;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(CoffeeMessagePublisher.class)
public class CoffeeMessageFilter {

    @Transformer(inputChannel = CoffeeMessagePublisher.cfToCfFilter,outputChannel = CoffeeMessagePublisher.cfFilterToMcFilter)
    public CoffeeMessage filterCoffeeMessage(Message<CoffeeMessage> coffeeMsg){
        System.out.println("11111111"+coffeeMsg.getPayload().id+"  "+coffeeMsg.getPayload().message);
        return coffeeMsg.getPayload();
    }

    @Filter(inputChannel = CoffeeMessagePublisher.mcFilterToCfFilter,outputChannel = CoffeeMessagePublisher.cfFilterToCf)
    public Boolean filterCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMachineMsg){
        System.out.println("coffeeMachineMessage: "+coffeeMachineMsg.getPayload().coffeeMachineId+"   "+coffeeMachineMsg.getPayload().status+coffeeMachineMsg.getPayload().distance);
        CoffeeMachineMessage coffeeMachineMessage = coffeeMachineMsg.getPayload();
        if(coffeeMachineMessage.distance>150.00){
            return false;
        }
        return true;
       // return coffeeMachineMsg.getPayload();
    }
}
