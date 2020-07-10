package com.servicematrix.personproxy;

import com.servicematrix.personproxy.domain.CoffeeMessage;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("Before Method Invoke");
        proxy.invokeSuper(object, objects);
        System.out.println("After Method Invoke");
        this.orderCoffeeService();
        return object;
    }

    public void orderCoffeeService(){
        CoffeeMessage coffeeMessage = new CoffeeMessage();
        coffeeMessage.id = "personservice_orderCoffee";
        coffeeMessage.message= "I need a cup of latte.";
        Message<CoffeeMessage> msg = MessageBuilder.withPayload(coffeeMessage)
                .setHeader("destination","coffeeMachineConsumer")
                .build();
        coffeeMessagePublisher.coffeePublish().send(msg);
        System.out.println(msg.getPayload().id+"   "+msg.getPayload().message);
    }

}