package com.example.demo.activemq.config;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/*
 * The @EnableJms enables JMS listener annotated endpoints that are created under the cover by JmsListenerContainerFactory.
 * The JmsListenerContainerFactory is responsible to create the listener container responsible for a particular endpoint.
 * The @EnableJms annotation also enables detection of JmsListener annotations on any Spring-managed beans in the container.
 */
@EnableJms
@Configuration
public class ActiveMQConfig {
	public static final String ORDER_TOPIC = "order-topic";

	/*
	 * Notice: we have created a custom ObjectMapper and registered the module JavaTimeModule.
	 * This helps Jackson support the jsr-310 time and date modules from Java 8 e.g.: LocalDateTime.
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
	}
	
	/*
	 * The MappingJackson2MessageConverter uses Jackson to convert messages to and from JSON.
	 */
	@Bean
	public MessageConverter messageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		converter.setObjectMapper(objectMapper());
		return converter;
	}

    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(messageConverter());
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }
}
