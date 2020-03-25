package com.avtivemq.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 消息队列工具类 activemq/conf/activemq.xml文件中 broker节点添加useJmx="true"，并且修改managementContext节点内容为
 * <managementContext>
 * <managementContext createConnector="true" connectorPort="11099" connectorPath=
 * "/jmxrmi" jmxDomainName="org.apache.activemq"/> </managementContext>
 */

public class ActiveMQUtil {

	private static Log log = LogFactory.getLog(ActiveMQUtil.class);
	private static final String connectorPath = "/jmxrmi";
	private static final String jmxDomain = "org.apache.activemq";

    private static Map<String, Object> getQueueNumbers(String user, String password, String ip, String port) throws Exception{
    	Map<String, Object> waitingNummberMap = new HashMap<>();
		BrokerViewMBean mBean=null;
        MBeanServerConnection connection=null;
        try{
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + ip + ":" + port + connectorPath);
            Map<String, Object> credentials = new HashMap<>();
            credentials.put(JMXConnector.CREDENTIALS, new String[] {user, password});
            JMXConnector connector = JMXConnectorFactory.connect(url,credentials);
            connector.connect();
            connection = connector.getMBeanServerConnection();
            ObjectName name = new ObjectName(jmxDomain + ":brokerName=localhost,type=Broker");
            mBean = MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);
        }catch (IOException e){
            log.error("ActiveMQUtil.getAllQueueSize",e);
        }catch (MalformedObjectNameException e){
            log.error("ActiveMQUtil.getAllQueueSize",e);
        }

        if(mBean!=null){
            for (ObjectName queueName : mBean.getQueues()) {
                QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, queueName, QueueViewMBean.class, true);
                
                waitingNummberMap.put(queueMBean.getName(), queueMBean.getQueueSize());
                log.info("Queue Name --- " + queueMBean.getName());// 消息队列名称
                log.info("Queue Size --- " + queueMBean.getQueueSize());// 队列中剩余的消息数
//                System.out.println("Number of Consumers --- " + queueMBean.getConsumerCount());// 消费者数
//                System.out.println("Number of Dequeue ---" + queueMBean.getDequeueCount());// 出队数
            }
        }
        
        return waitingNummberMap;
    }
    
	public static void main(String[] args) {
		String user = "admin";
		String password = "activemq";
		String ip = "10.133.117.190";
		String post = "11099";
		try {
			Map<String, Object> numberMap = getQueueNumbers(user, password, ip, post);
			for(String key : numberMap.keySet()){
				String value = numberMap.get(key).toString();
				System.out.println(key+" : "+value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
