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
        System.out.println("����2��������");
        //1 �������ӹ��������ո�����url��ַ������Ĭ���û���������
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2 ͨ�����ӹ�������ȡ����connection ����������
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3 �����Ựsession
        //������������һ��������/�ڶ�����ǩ��
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4 ����Ŀ�ĵ�(�����Ƕ��л�������topic)
        Queue queue = session.createQueue(QUEUE_NAME);
        //5 ����������
        MessageConsumer messageConsumer = session.createConsumer(queue);
        //6 �����߼���
        messageConsumer.setMessageListener(new Listener1());
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("*********��Ϣ������MQ���");

    }
}
