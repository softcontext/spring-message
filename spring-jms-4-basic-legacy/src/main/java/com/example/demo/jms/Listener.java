package com.example.demo.jms;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class Listener {
	@Autowired
	private Producer producer;

	@JmsListener(destination = "my_inbound.queue")
	public void receiveMessage(final Message jsonMessage) throws JMSException {
		System.out.println("receiveMessage << " + jsonMessage + " >>");

		String messageData = null;

		if (jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) jsonMessage;
			messageData = textMessage.getText();
		}
		producer.sendMessage("my_outbound.queue", messageData);
	}

//	@JmsListener(destination = "my_inbound.topic")
//	@SendTo("my_outbound.topic")
//	public String receiveMessageFromTopic(final Message jsonMessage) throws JMSException {
//		System.out.println("receiveMessageFromTopic << " + jsonMessage + " >>");
//		
//		String messageData = null;
//		String response = null;
//		
//		if (jsonMessage instanceof TextMessage) {
//			TextMessage textMessage = (TextMessage) jsonMessage;
//			messageData = textMessage.getText();
//			Map<?, ?> map = new Gson().fromJson(messageData, Map.class);
//			response = "Hello " + map.get("name");
//		}
//		return response;
//	}
}
