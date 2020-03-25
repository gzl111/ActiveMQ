package com.avtivemq.producer;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer1 {
//	public static final String ACTIVEMQ_URL = "tcp://10.133.117.190:61616";
	public static final String ACTIVEMQ_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    public static final String QUEUE_NAME = "queue01";


    public static void main(String[ ] args) throws JMSException {
        //1 �������ӹ��������ո�����url��ַ������Ĭ���û���������
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2 ͨ�����ӹ�������ȡ����connection ����������
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3 �����Ựsession
        //������������һ��������/�ڶ�����ǩ��
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4 ����Ŀ�ĵ�(�����Ƕ��л�������topic)
        Destination destination = session.createQueue(QUEUE_NAME);
        //5 ������Ϣ��������
        MessageProducer messageProducer = session.createProducer(destination);
        //6 ͨ��ʹ��messageProducer����3����Ϣ���͵�MQ�Ķ�������
        for(int i = 1; i <= 100; i++) {
            //7 ������Ϣ���ñ�ѧ��������ʦҪ��д�õ���������Ϣ
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            //8 ͨ��messageProducer���͸�mq
            messageProducer.send(textMessage);
        }
        //9 �ر���Դ
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("*********��Ϣ������MQ���");

    }
}
