package com.example.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.activemq.model.Order;
import com.example.demo.activemq.publish.OrderSender;

@SpringBootApplication
public class Application implements ApplicationRunner {
	private static Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	private OrderSender orderSender;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		log.info("[[ Spring Boot ActiveMQ Publish Subscribe Topic Configuration Example ]]");

		for (int i = 0; i < 5; i++) {
			Order order = new Order("me", "you", new BigDecimal(i), LocalDateTime.now());
			orderSender.sendTopic(order);
		}

		log.info("Waiting for all ActiveMQ JMS Messages to be consumed");
		
		TimeUnit.SECONDS.sleep(3);
		
		System.exit(-1);
	}
}
