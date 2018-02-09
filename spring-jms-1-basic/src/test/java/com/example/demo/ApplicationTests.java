package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.jms.consumer.Receiver;
import com.example.demo.jms.producer.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	private static ApplicationContext applicationContext;

	@Autowired
	void setContext(ApplicationContext applicationContext) {
		ApplicationTests.applicationContext = applicationContext;
	}

	@AfterClass
	public static void afterClass() {
		((ConfigurableApplicationContext) applicationContext).close();
	}

	/*
	 * @ClassRule 
	 * to set up something that can be reused by all the test methods, if you can achieve that in a static method.
	 * : using @ClassRule to avoid the embedded broker gets created.
	 * @Rule 
	 * to set up something that needs to be created a new, or reset, for each test method. 
	 */
	@ClassRule
	public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

	@Autowired
	private Sender sender;

	@Autowired
	private Receiver receiver;

	@DirtiesContext
	@Test
	public void testReceive() throws Exception {
		sender.send("helloworld.q", "Hello Spring JMS ActiveMQ!");

		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		assertThat(receiver.getLatch().getCount()).isEqualTo(0);
	}
}
