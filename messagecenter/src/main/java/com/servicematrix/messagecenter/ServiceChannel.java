package com.servicematrix.messagecenter;

import com.rabbitmq.client.AMQP;

public class ServiceChannel extends ConnectionChannel {

    public ServiceChannel(String name) throws Exception {
        super();
        this.queueName = name;
        this.EXCHANGE_NAME = name;
        //声明一个队列 - 持久化
        channel.queueDeclare(queueName, true, false, false, null);
        //设置通道预取计数
        channel.basicQos(1);
        this.routingKey = name;
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", false, false, null);
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
    }

    public void sendMessage(String body) throws Exception{
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
        //将消息队列绑定到Exchange
        channel.basicPublish(EXCHANGE_NAME, routingKey, properties.build(), body.getBytes());
        System.out.println("PDM消息发送成功 -- [ " + EXCHANGE_NAME + " ] - " + routingKey);
    }


}
