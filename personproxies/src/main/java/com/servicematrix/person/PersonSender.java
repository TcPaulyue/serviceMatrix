package com.servicematrix.person;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PersonSender extends ConnectionChannel {

    private String name;

    private Double xLoc;

    private Double yLoc;

    private Map<String,Object> headers = new HashMap<String, Object>();

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public PersonSender() throws Exception {
        super();
    }

    public void init(String name,Double xLoc,Double yLoc) throws IOException {
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

    @Override
    public Channel getChannel() {
        return super.getChannel();
    }

    public void sendMessage(String body) throws Exception{
        headers.put("destination", "messageCenter");

        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
        properties.headers(headers);
        //将消息队列绑定到Exchange
        channel.basicPublish(EXCHANGE_NAME, routingKey, properties.build(), body.getBytes());
        System.out.println("PDM消息发送成功 -- [ " + EXCHANGE_NAME + " ] - " + routingKey);
    }

    public String getEXCHANGE_NAME() {
        return EXCHANGE_NAME;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setxLoc(Double xLoc) {
        this.xLoc = xLoc;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setyLoc(Double yLoc) {
        this.yLoc = yLoc;
    }

    public Double getxLoc() {
        return xLoc;
    }

    public Double getyLoc() {
        return yLoc;
    }
}
