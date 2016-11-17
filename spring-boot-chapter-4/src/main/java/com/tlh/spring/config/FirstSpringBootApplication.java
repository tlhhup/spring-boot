package com.tlh.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@ResponseBody标识将返回的数据直接放入responseBody中
@RestController//使用组合注解,该注解组合@Controller和@ResponseBody
@SpringBootApplication
public class FirstSpringBootApplication {

	@RequestMapping("/index")
	public String index(){
		return "Hello Spring Boot";
	}
	
	public static void main(String[] args){
		SpringApplication.run(FirstSpringBootApplication.class, args);
	}
	
}
