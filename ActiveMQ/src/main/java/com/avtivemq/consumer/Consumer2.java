package com.avtivemq.consumer;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.avtivemq.listener.Listener1;

public class Consumer2 {
//    public static final String ACTIVEMQ_URL = "tcp://10.133.117.190:61616";
	public static final String ACTIVEMQ_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[ ] args) throws JMSException, IOException {
        System.out.println("我是2号消费者");
        //1 创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2 通过连接工厂，获取连接connection 并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3 创建会话session
        //两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4 创建目的地(具体是队列还是主题topic)
        Queue queue = session.createQueue(QUEUE_NAME);
        //5 创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        //6 消费者监听
        messageConsumer.setMessageListener(new Listener1());
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("*********消息发布到MQ完成");

    }
}
