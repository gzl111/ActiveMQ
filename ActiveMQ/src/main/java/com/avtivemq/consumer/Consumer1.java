package com.avtivemq.consumer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer1 {

//    public static final String ACTIVEMQ_URL = "tcp://10.133.117.190:61616";
	public static final String ACTIVEMQ_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static final String QUEUE_NAME = "queue02";

    public static void main(String[ ] args) throws JMSException {

        System.out.println("����1��������");
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
        while (true) {
            TextMessage textMessage = (TextMessage)messageConsumer.receive();
            //TextMessage textMessage = (TextMessage)messageConsumer.receive(4000L);
            if(null != textMessage) {
                System.out.println("�����߽��յ���Ϣ��"+textMessage.getText());
            } else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("*********����MQ��Ϣ���");

    }
}
