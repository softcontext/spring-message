package com.example.demo.activemq.subscribe;

import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.demo.activemq.config.ActiveMQConfig;
import com.example.demo.activemq.model.Order;

@Component
public class OrderConsumer {
	private static Logger log = LoggerFactory.getLogger(OrderConsumer.class);

	@JmsListener(destination = ActiveMQConfig.ORDER_TOPIC, containerFactory = "topicListenerFactory")
	public void receiveTopicMessage(@Payload Order order, @Headers MessageHeaders headers, 
			Message<?> message, Session session) {

		log.info("received << " + order + " >>");
	}

}
