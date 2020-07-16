package com.servicematrix.person;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Map;

public interface OrderCoffee {
    public void orderCoffee(Channel channel, String EXCHANGE_NAME, String routingKey, Map<String,Object> headers) throws IOException;
}
