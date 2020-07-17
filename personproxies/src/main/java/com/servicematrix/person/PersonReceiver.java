package com.servicematrix.person;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class PersonReceiver extends ConnectionChannel{

    private String name;

    public PersonReceiver(String name) throws Exception {
        super();
        this.name = name;
    }

    public void init() throws IOException {
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

    public void getMessage() throws Exception{

        //声明一个临时队列，该队列会在使用完比后自动销毁 - 非必需
        queueName = channel.queueDeclare().getQueue();

        // - 声明要关注的队列 - 非必需
        //channel.queueDeclare(queueName, true, false, false, null);

        //server push消息时的队列长度 - 同一时刻服务器只会发一条消息给消费者  - 非必需
        channel.basicQos(1);

        //将消息队列绑定到Exchange - 将队列绑定到交换机 - 绑定一个routing key
        channel.queueBind(queueName, name, routingKey);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                // process the message
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    public static void main(String[] args) throws Exception {
        PersonReceiver personReceiver = new PersonReceiver("xiaoming");
        personReceiver.init();
        personReceiver.getMessage();
    }
}
