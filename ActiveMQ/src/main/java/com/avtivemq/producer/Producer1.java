package com.avtivemq.producer;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer1 {
//	public static final String ACTIVEMQ_URL = "tcp://10.133.117.190:61616";
	public static final String ACTIVEMQ_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    public static final String QUEUE_NAME = "queue01";


    public static void main(String[ ] args) throws JMSException {
        //1 创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2 通过连接工厂，获取连接connection 并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3 创建会话session
        //两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4 创建目的地(具体是队列还是主题topic)
        Destination destination = session.createQueue(QUEUE_NAME);
        //5 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(destination);
        //6 通过使用messageProducer生产3条信息发送到MQ的队列里面
        for(int i = 1; i <= 100; i++) {
            //7 创建消息，好比学生按照老师要求写好的面试题消息
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            //8 通过messageProducer发送给mq
            messageProducer.send(textMessage);
        }
        //9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("*********消息发布到MQ完成");

    }
}
