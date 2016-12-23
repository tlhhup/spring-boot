package com.tlh.springboot.component;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class ScheduleRabbitSender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static int i=0;
	
	@Scheduled(fixedRate=2000)
	public void sendMessage(){
		rabbitTemplate.convertAndSend("", "queueName", "通过Schedule发送的消息"+(i++));
	}
	
}
