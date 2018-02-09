package com.example.demo.activemq.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.activemq.config.ActiveMQConfig;
import com.example.demo.activemq.model.Order;

@Service
public class OrderSender {
	private static Logger log = LoggerFactory.getLogger(OrderSender.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	/*
	 * We use the JmsTemplate to publish JMS messages to the topic. 
	 * We simply need to pass in a destination and message arguments 
	 * and the JmsTemplate handles the rest.
	 */
	public void sendTopic(Order order) {
		log.info("sending with convertAndSend() to topic << " + order + " >>");
		jmsTemplate.convertAndSend(ActiveMQConfig.ORDER_TOPIC, order);
	}

}