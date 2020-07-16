package com.servicematrix.person;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class OrderCoffeeService implements OrderCoffee {

    public void orderCoffee(Channel channel,String EXCHANGE_NAME,String routingKey,Map<String,Object> headers) throws IOException {
        //channel.queueDeclare("products_queue", false, false, false, null);

        headers.put("destination", "coffeeMachine");
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
        properties.headers(headers);

        String message = "order a cup of coffee.";
        System.out.println(" [x] Sent '" + message + "'");
        channel.basicPublish(EXCHANGE_NAME, routingKey, properties.build(), message.getBytes());
        System.out.println("orderCoffeeService: order a cup of coffee.");
    }
}
