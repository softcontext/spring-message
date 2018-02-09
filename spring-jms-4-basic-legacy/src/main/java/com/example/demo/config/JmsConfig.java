package com.example.demo.config;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
//@EnableJms : It enables detection of JmsListener annotations on any Spring-managed bean in the container.
@EnableJms
public class JmsConfig {
	String BROKER_URL = "tcp://localhost:61616";
	String BROKER_USERNAME = "admin";
	String BROKER_PASSWORD = "admin";

	//ActiveMQConnectionFactory: It is used for creating connections. 
	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(BROKER_URL);
		connectionFactory.setPassword(BROKER_USERNAME);
		connectionFactory.setUserName(BROKER_PASSWORD);
		return connectionFactory;
	}

	//JmsTemplate : It is a helper class that simplifies receiving and sending of messages through JMS and gets rid of the boilerplate code. 
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
//		template.setPubSubDomain(true); // for Topic
		return template;
	}

	//DefaultJmsListenerContainerFactory : It is responsible to create the listener container responsible for a particular endpoint.
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrency("1-1");
//		factory.setPubSubDomain(true); // for Topic
		return factory;
	}
}
