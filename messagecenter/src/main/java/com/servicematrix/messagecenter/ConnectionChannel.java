package com.servicematrix.messagecenter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public abstract class ConnectionChannel {
    protected static final String HOST = "127.0.0.1";

    // 连接
    protected Connection connection;
    // 连接通道
    protected Channel channel;
    // 连接通道路由地址
    protected String routingKey;

    protected String queueName;

    protected String EXCHANGE_NAME;

    public ConnectionChannel() throws Exception {

        // 创建一个连接工厂 connection factory
        ConnectionFactory factory = new ConnectionFactory();

        // 设置rabbitmq-server服务IP地址、用户名、密码、端口
        factory.setHost(HOST);

        // 声明一个连接
        connection = factory.newConnection();

        // 声明消息通道
        channel = connection.createChannel();

    }

    public Channel getChannel() {
        return channel;
    }
}
