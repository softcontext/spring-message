package com.example.demo.activemq.receiver;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/*
 * tcp://localhost:61616
 * http://localhost:8161/admin/
 */
@Component
public class Listener {

	/*
	 * Received message ActiveMQTextMessage 
	 * {
	 * 		commandId = 9, 
	 * 		responseRequired = false, 
	 * 		messageId = ID:DESKTOP-827TD4G-48873-1517820938502-4:1:1:1:1, 
	 * 		originalDestination = null, 
	 * 		originalTransactionId = null, 
	 * 		producerId = ID:DESKTOP-827TD4G-48873-1517820938502-4:1:1:1, 
	 * 		destination = queue://inbound.queue, 
	 * 		transactionId = null, 
	 * 		expiration = 0, 
	 * 		timestamp = 1517888365481, 
	 * 		arrival = 0, 
	 * 		brokerInTime = 1517888365481, 
	 * 		brokerOutTime = 1517888365484, 
	 * 		correlationId = , 
	 * 		replyTo = null, 
	 * 		persistent = false, 
	 * 		type = , priority = 0, groupID = null, groupSequence = 0, 
	 * 		targetConsumerId = null, compressed = false, userID = null, 
	 * 		content = org.apache.activemq.util.ByteSequence@3410348c, 
	 * 		marshalledProperties = null, dataStructure = null, redeliveryCounter = 0, size = 0, 
	 * 		properties = null, readOnlyProperties = true, readOnlyBody = true, 
	 * 		droppable = false, jmsXGroupFirstForConsumer = false, 
	 * 		text = {"name":"john"}
	 * }
	 */
	@JmsListener(destination = "inbound.queue")
	@SendTo("outbound.queue")
	public String receiveMessage(final Message jsonMessage) throws JMSException {
		System.out.println("Received message " + jsonMessage);

		String messageData = null;
		String response = null;

		if (jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) jsonMessage;
			messageData = textMessage.getText();
			Map<?, ?> map = new Gson().fromJson(messageData, Map.class);
			response = "Hello " + map.get("name");
		}
		return response;
	}
	
//	@JmsListener(destination = "inbound.topic")
//	@SendTo("outbound.topic")
//	public String receiveMessageFromTopic(final Message jsonMessage) throws JMSException {
//		System.out.println("Received message " + jsonMessage);
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
