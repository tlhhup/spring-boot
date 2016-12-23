package com.tlh.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.tlh.springboot.component.ScheduleRabbitSender;

@Configuration
@EnableScheduling
public class SchedulingConfig{
	
	@Bean
	public ScheduleRabbitSender task(){
		return new ScheduleRabbitSender();
	}
}
