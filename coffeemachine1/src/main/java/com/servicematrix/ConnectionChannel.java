package com.servicematrix;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public abstract class ConnectionChannel {
    //安装 RabbitMQ 的主机IP
    protected static final String HOST = "127.0.0.1";

    //消息服务请求URL
//    public static final String CMS_HOST = "http://" + HOST + ":8080";

    // routingKey
    //   public static final String ROUTING_KEY = "YOU.SELF.KEY";

    protected final static String EXCHANGE_NAME = "CoffeeMachine";

    // 连接
    protected Connection connection;
    // 连接通道
    protected Channel channel;
    // 连接通道路由地址
    protected String routingKey;

    public ConnectionChannel() throws Exception {

        // 创建一个连接工厂 connection factory
        ConnectionFactory factory = new ConnectionFactory();

        // 设置rabbitmq-server服务IP地址、用户名、密码、端口
        factory.setHost(HOST);

        // 声明一个连接
        connection = factory.newConnection();

        // 声明消息通道
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic", false, false, null);

    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Channel getChannel() {
        return channel;
    }
}
