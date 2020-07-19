package com.servicematrix;

import com.rabbitmq.client.AMQP;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CoffeeMachineSender extends ConnectionChannel {

    private String name;

    private Double xLoc;

    private Double yLoc;

    private Map<String,Object> headers = new HashMap<String, Object>();


    public CoffeeMachineSender(String name,Double xLoc,Double yLoc) throws Exception {
        super();
        this.name = name;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        headers.put("name",this.name);
        headers.put("XLOC",this.xLoc);
        headers.put("YLOC",this.yLoc);

        this.queueName = headers.get("name").toString();
        this.EXCHANGE_NAME = headers.get("name").toString();

        //声明一个队列 - 持久化
        channel.queueDeclare(queueName, true, false, false, null);
        //设置通道预取计数
        channel.basicQos(1);
        this.routingKey = headers.get("name").toString();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", false, false, null);
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
    }

    public void sendMessage(String body) throws Exception{
        headers.put("destination", "person");
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
        properties.headers(headers);
        //将消息队列绑定到Exchange
        channel.basicPublish(EXCHANGE_NAME, routingKey, properties.build(), body.getBytes());
        System.out.println("PDM消息发送成功 -- [ " + EXCHANGE_NAME + " ] - " + routingKey);
    }

}
