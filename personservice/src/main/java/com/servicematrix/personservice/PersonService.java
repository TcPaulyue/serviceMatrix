package com.servicematrix.personservice;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.PostConstruct;


@EnableBinding(CoffeeMessagePublisher.class)
public class PersonService extends QuartzJobBean {

   private CoffeeMessagePublisher coffeeMessagePublisher;

   @Autowired
    public PersonService(CoffeeMessagePublisher coffeeMessagePublisher){
        this.coffeeMessagePublisher = coffeeMessagePublisher;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        this.orderCoffeeService();
    }

    public void orderCoffeeService(){
        CoffeeMessage coffeeMessage = new CoffeeMessage();
        coffeeMessage.id = "personservice_orderCoffee";
        coffeeMessage.message= "I need a cup of latte.";
        Message<CoffeeMessage> msg = MessageBuilder.withPayload(coffeeMessage)
                .setHeader("personService","orderCoffee")
                .build();
        coffeeMessagePublisher.coffeePublish().send(msg);
    }


    @StreamListener(value = CoffeeMessagePublisher.cfFilterToCf)
    public void checkCoffeeMachineMessage(Message<CoffeeMachineMessage> coffeeMsg){
        CoffeeMachineMessage coffeeMachineMessage = coffeeMsg.getPayload();
        System.out.println("-------"+ coffeeMachineMessage.getCoffeeMachineId() +"  "+coffeeMachineMessage.status);
    }

    @StreamListener(value = CoffeeMessagePublisher.volunteerToCfFilter)
    public void checkVolunteerMessage(Message<VolunteerMessage> volunteerMsg){
       VolunteerMessage volunteerMessage = volunteerMsg.getPayload();
       System.out.println("--------"+volunteerMessage.volunteerName+"   "+volunteerMessage.status);
    }
}
