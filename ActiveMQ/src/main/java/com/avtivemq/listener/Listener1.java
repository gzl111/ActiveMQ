package com.avtivemq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Listener1 implements MessageListener {

	public void onMessage(Message message) {
		TextMessage textMessage =(TextMessage)message;
        if(null != message && message instanceof TextMessage) {
            try {
                System.out.println("**********消费者接收到消息:" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
	}

}
