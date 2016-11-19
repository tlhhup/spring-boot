package com.tlh.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.tlh.spring.**"})
public class Chapter6Application {

	public static void main(String[] args){
		SpringApplication.run(Chapter6Application.class, args);
	}
	
}
