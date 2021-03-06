package com.servicematrix.personfilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;


@EnableBinding(CoffeeMessagePublisher.class)
public class CoffeeMessageFilter {

    private CoffeeMessagePublisher coffeeMessagePublisher;
    protected Boolean state = false;

    @Autowired
    public CoffeeMessageFilter(CoffeeMessagePublisher coffeeMessagePublisher){
        this.coffeeMessagePublisher = coffeeMessagePublisher;
    }


//    @Transformer(inputChannel = CoffeeMessagePublisher.cfToCfFilter,outputChannel = CoffeeMessagePublisher.personPublisher)
//    public Message<CoffeeMessage> filterCoffeeMessage(Message<CoffeeMessage> coffeeMsg){
//        System.out.println("===============filter of personService==============");
//        System.out.println(coffeeMsg.getPayload().id+"  "+coffeeMsg.getPayload().message);
//        return coffeeMsg;
//    }
//
//    @Filter(inputChannel = CoffeeMessagePublisher.personConsumer,outputChannel = CoffeeMessagePublisher.cfFilterToCf)
//    public Boolean filterCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMachineMsg){
//        System.out.println("coffeeMachineMessage: "+ coffeeMachineMsg.getPayload().getCoffeeMachineId() +"   "+coffeeMachineMsg.getPayload().status+coffeeMachineMsg.getPayload().distance);
//        CoffeeMachineMessage coffeeMachineMessage = coffeeMachineMsg.getPayload();
//        if((coffeeMachineMessage.distance > 150.00) || state){
//            return false;
//        }
//        state = true;
//        return true;
//    }

    @StreamListener(CoffeeMessagePublisher.cfToCfFilter)
    public void filterCoffeeMessage(Message<?> coffeeMsg){

        if(coffeeMsg.getHeaders().get("destination").equals("coffeeMachineConsumer")){
            CoffeeMessage coffeeMessage = (CoffeeMessage)coffeeMsg.getPayload();
            System.out.println(coffeeMessage.id+"  "+coffeeMessage.message);
        }
        else if(coffeeMsg.getHeaders().get("destination").equals("messageCenter")){
            PersonInfo personInfo = (PersonInfo)coffeeMsg.getPayload();
            System.out.println(personInfo.name+" "+personInfo.Xloc+"  "+personInfo.Yloc);
        }
        System.out.println("11111");
        coffeeMessagePublisher.coffeeFilter().send(coffeeMsg);
    }


    @StreamListener(CoffeeMessagePublisher.personConsumer)
    public void  filterCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMachineMsg){
        System.out.println("coffeeMachineMessage: "+ coffeeMachineMsg.getPayload().getCoffeeMachineId() +"   "+coffeeMachineMsg.getPayload().status+coffeeMachineMsg.getPayload().distance);
        CoffeeMachineMessage coffeeMachineMessage = coffeeMachineMsg.getPayload();
        if((coffeeMachineMessage.distance > 150.00) || state){
            coffeeMessagePublisher.machineFilter().subscribe(new MessageHandler() {
                @Override
                public void handleMessage(Message<?> message) throws MessagingException {
                    System.out.println(message.getPayload().toString());
                }
            });
        }
        state = true;
        coffeeMessagePublisher.machineConsume().send(coffeeMachineMsg);
    }

}
