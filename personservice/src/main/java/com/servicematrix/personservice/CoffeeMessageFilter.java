package com.servicematrix.personservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(CoffeeMessagePublisher.class)
public class CoffeeMessageFilter {

    private CoffeeMessagePublisher coffeeMessagePublisher;
    protected Boolean state = false;
    private static Boolean mc1 = false;
    private static Boolean mc2 = false;

    @Autowired
    public CoffeeMessageFilter(CoffeeMessagePublisher coffeeMessagePublisher){
        this.coffeeMessagePublisher = coffeeMessagePublisher;
    }


    @Transformer(inputChannel = CoffeeMessagePublisher.cfToCfFilter,outputChannel = CoffeeMessagePublisher.cfFilterToMcFilter)
    public CoffeeMessage filterCoffeeMessage(Message<CoffeeMessage> coffeeMsg){
        System.out.println("===============filter of personService==============");
        System.out.println(coffeeMsg.getPayload().id+"  "+coffeeMsg.getPayload().message);
        return coffeeMsg.getPayload();
    }

    @Filter(inputChannel = CoffeeMessagePublisher.mcFilterToCfFilter,outputChannel = CoffeeMessagePublisher.cfFilterToCf)
    public Boolean filterCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMachineMsg){
        System.out.println("coffeeMachineMessage: "+ coffeeMachineMsg.getPayload().getCoffeeMachineId() +"   "+coffeeMachineMsg.getPayload().status+coffeeMachineMsg.getPayload().distance);
        CoffeeMachineMessage coffeeMachineMessage = coffeeMachineMsg.getPayload();
        if(coffeeMachineMessage.getCoffeeMachineId().equals("coffeeMachine_1")){
            mc1 = true;
        }else if(coffeeMachineMessage.getCoffeeMachineId().equals("coffeeMachine_2")){
            mc2 = true;
        }
        if(mc1&&mc2&&!state){
            CoffeeMessage coffeeMessage = new CoffeeMessage();
            coffeeMessage.id = "personservice_orderCoffee_1";
            coffeeMessage.message= "a cup of latte.";
            Message<CoffeeMessage> msg = MessageBuilder.withPayload(coffeeMessage)
                    .setHeader("personService","orderCoffee")
                    .build();
            System.out.println("send message to volunteer");
            coffeeMessagePublisher.coffeeToVolunteer().send(msg);
            return false;
        }
        if((coffeeMachineMessage.distance > 150.00) || state){
            return false;
        }
        state = true;
        return true;
       // return coffeeMachineMsg.getPayload();
    }


}
