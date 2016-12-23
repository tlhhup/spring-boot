package com.tlh.springboot;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RabbitApplication.class, args);
	}
	
	@Bean
	Queue queue(){
		return new Queue("queueName");
	}

}
