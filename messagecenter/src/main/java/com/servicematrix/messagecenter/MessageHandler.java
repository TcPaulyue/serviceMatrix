package com.servicematrix.messagecenter;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MessageHandler extends ConnectionChannel{

    private PersonChannel personChannel;



    public MessageHandler(String name) throws Exception {
        super();
        this.queueName = name;
        this.EXCHANGE_NAME = name;
        this.routingKey = name;
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", false, false, null);

        this.personChannel = new PersonChannel("xiaoming");
    }

    /**
     * @Title : getMessage
     * @Function: 从 交换机 上获取消息
     * @throws Exception
     */
    public void getMessage() throws Exception{

        //声明一个临时队列，该队列会在使用完比后自动销毁 - 非必需
        queueName = channel.queueDeclare().getQueue();

        //server push消息时的队列长度 - 同一时刻服务器只会发一条消息给消费者  - 非必需
        channel.basicQos(1);

        //将消息队列绑定到Exchange - 将队列绑定到交换机 - 绑定一个routing key
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                System.out.println(properties.getHeaders().get("destination"));
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                String destination = properties.getHeaders().get("destination").toString();
                try {
                    personChannel.sendMessage(message+"abc");
                    System.out.println("MessageCenter消息发送成功 -- [ " + destination + " ] - " + message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // channel.basicPublish("xiaoming", "xiaoming", properties, message.getBytes());
            }
        };
        channel.basicConsume(queueName, true, consumer);

    }

    public static void main(String[] args) throws Exception {
        MessageHandler messageHandler = new MessageHandler("messageCenter");
        messageHandler.getMessage();
    }
}
