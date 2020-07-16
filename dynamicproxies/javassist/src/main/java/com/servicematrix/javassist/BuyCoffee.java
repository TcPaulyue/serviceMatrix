package com.servicematrix.javassist;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BuyCoffee implements OrderCoffeeInterface{


    public void orderCoffeeService() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("products_queue", false, false, false, null);

        String message = "product details";
        System.out.println(" [x] Sent '" + message + "'");

        channel.basicPublish("", "proucts_queue", null, message.getBytes());

        channel.close();
        connection.close();
        System.out.println("orderCoffeeService: order a cup of coffee.");
//        Message<CoffeeMessage> msg = MessageBuilder.withPayload(coffeeMessage)
//                .setHeader("destination","coffeeMachineConsumer")
//                .build();
//        coffeeMessagePublisher.coffeePublish().send(msg);
//        System.out.println(msg.getPayload().getId()+"   "+msg.getPayload().getMessage()+ " "+msg.getPayload().getPersonInfo().toString());
    }
}
