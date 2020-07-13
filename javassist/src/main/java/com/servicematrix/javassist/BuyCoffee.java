package com.servicematrix.javassist;

public class BuyCoffee {

    public void orderCoffeeService(){
        CoffeeMessage coffeeMessage = new CoffeeMessage();
        coffeeMessage.setId("personservice_orderCoffee");
        coffeeMessage.setMessage("I need a cup of latte.");
        System.out.println("orderCoffeeService: order a cup of coffee.");
//        Message<CoffeeMessage> msg = MessageBuilder.withPayload(coffeeMessage)
//                .setHeader("destination","coffeeMachineConsumer")
//                .build();
//        coffeeMessagePublisher.coffeePublish().send(msg);
//        System.out.println(msg.getPayload().getId()+"   "+msg.getPayload().getMessage()+ " "+msg.getPayload().getPersonInfo().toString());
    }
}
