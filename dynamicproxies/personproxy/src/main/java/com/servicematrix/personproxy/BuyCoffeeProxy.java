package com.servicematrix.personproxy;

import com.servicematrix.personproxy.domain.CoffeeMessage;
import com.servicematrix.personproxy.domain.PersonInfo;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.lang.reflect.Method;


@EnableBinding(CoffeeMessagePublisher.class)
public class BuyCoffeeProxy implements MethodInterceptor {

    private CoffeeMessagePublisher coffeeMessagePublisher;

    public BuyCoffeeProxy(CoffeeMessagePublisher coffeeMessagePublisher) {
        this.coffeeMessagePublisher = coffeeMessagePublisher;
    }

    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        if(method.getName().equals("updatePersonInfo")){
            System.out.println("send Coffee message");
            objects[0] = "abc";
            proxy.invokeSuper(object, objects);
            this.orderCoffeeService(objects);
            System.out.println("After Method Invoke");
            return object;
        }else{
            System.out.println("send person Info.");
            proxy.invokeSuper(object, objects);
            this.sendPersonInfo(objects);
            return object;
        }
    }

    public void sendPersonInfo(Object[] objects){
        PersonInfo personInfo = this.transToPersonInfo(objects);
        Message<PersonInfo> msg = MessageBuilder.withPayload(personInfo)
                .setHeader("destination","messageCenter")
                .build();
        coffeeMessagePublisher.coffeePublish().send(msg);
        System.out.println(msg.getPayload().getName()+"  "+msg.getPayload().getXloc()+"  "+msg.getPayload().getYloc());
    }


    public void orderCoffeeService(Object[] objects){
        CoffeeMessage coffeeMessage = new CoffeeMessage();
        coffeeMessage.setId("personservice_orderCoffee");
        coffeeMessage.setMessage("I need a cup of latte.");
        PersonInfo personInfo = this.transToPersonInfo(objects);
        coffeeMessage.setPersonInfo(personInfo);
        Message<CoffeeMessage> msg = MessageBuilder.withPayload(coffeeMessage)
                .setHeader("destination","coffeeMachineConsumer")
                .build();
        coffeeMessagePublisher.coffeePublish().send(msg);
        System.out.println(msg.getPayload().getId()+"   "+msg.getPayload().getMessage()+ " "+msg.getPayload().getPersonInfo().toString());
    }

    public PersonInfo transToPersonInfo( Object[] objects) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(objects[0].toString());
        personInfo.setXloc((Double)objects[1]);
        personInfo.setYloc((Double)objects[2]);
        return personInfo;
    }

}