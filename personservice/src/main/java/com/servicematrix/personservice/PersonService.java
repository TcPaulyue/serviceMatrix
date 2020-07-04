package com.servicematrix.personservice;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.scheduling.quartz.QuartzJobBean;



@EnableBinding(CoffeeMessagePublisher.class)
public class PersonService extends QuartzJobBean {

   private CoffeeMessagePublisher coffeeMessagePublisher;

   private static Boolean flag = true;
   @Autowired
    public PersonService(CoffeeMessagePublisher coffeeMessagePublisher){
        this.coffeeMessagePublisher = coffeeMessagePublisher;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        this.orderCoffeeService();
        try {
            Thread.sleep(100000);
            if(flag){
                CoffeeMessage coffeeMessage = new CoffeeMessage();
                coffeeMessage.id = "personservice_orderCoffee_1";
                coffeeMessage.message= "a cup of latte.";
                Message<CoffeeMessage> msg = MessageBuilder.withPayload(coffeeMessage)
                        .setHeader("destination","coffeeMachineConsumer")
                        .build();
                System.out.println("send message to volunteer");
                coffeeMessagePublisher.coffeeToVolunteer().send(msg);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void orderCoffeeService(){
        CoffeeMessage coffeeMessage = new CoffeeMessage();
        coffeeMessage.id = "personservice_orderCoffee";
        coffeeMessage.message= "I need a cup of latte.";
        Message<CoffeeMessage> msg = MessageBuilder.withPayload(coffeeMessage)
                .setHeader("destination","coffeeMachineConsumer")
                .build();
        coffeeMessagePublisher.coffeePublish().send(msg);
    }


    @StreamListener(value = CoffeeMessagePublisher.cfFilterToCf)
    public void checkCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMsg) throws InterruptedException {
        CoffeeMachineMessage coffeeMachineMessage = coffeeMsg.getPayload();
        System.out.println("-------"+ coffeeMachineMessage.getCoffeeMachineId() +"  "+coffeeMachineMessage.status);
        flag = false;
    }

    @StreamListener(value = CoffeeMessagePublisher.volunteerToCfFilter)
    public void checkVolunteerMessage(Message<VolunteerMessage> volunteerMsg){
       VolunteerMessage volunteerMessage = volunteerMsg.getPayload();
       System.out.println("--------"+volunteerMessage.volunteerName+"   "+volunteerMessage.status);
    }
}
