package com.tlh.springboot.component;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {

	@RabbitListener(queues="queueName")
	public void receiveMessage(Message message){
		System.out.println(message);
	}
	
}
