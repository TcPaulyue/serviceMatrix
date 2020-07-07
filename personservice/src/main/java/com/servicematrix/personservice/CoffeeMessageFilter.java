//package com.servicematrix.personservice;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.integration.annotation.Filter;
//import org.springframework.integration.annotation.Transformer;
//import org.springframework.messaging.Message;
//import org.springframework.stereotype.Service;
//
//@Service
//@EnableBinding(CoffeeMessagePublisher.class)
//public class CoffeeMessageFilter {
//
//    private CoffeeMessagePublisher coffeeMessagePublisher;
//    protected Boolean state = false;
//
//    @Autowired
//    public CoffeeMessageFilter(CoffeeMessagePublisher coffeeMessagePublisher){
//        this.coffeeMessagePublisher = coffeeMessagePublisher;
//    }
//
//
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
//
//
//}
