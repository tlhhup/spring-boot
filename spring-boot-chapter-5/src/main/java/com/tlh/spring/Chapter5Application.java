package com.tlh.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tlh.spring.dao.UserDao;
import com.tlh.spring.entity.User;

@RestController
@SpringBootApplication(scanBasePackages="com.tlh.spring",exclude={Repository.class})
public class Chapter5Application {

	@RequestMapping("/index")
	public String index(){
		return "Hello world";
	}
	
	@Autowired
	private User user;
	
	@Autowired
	private UserDao userDao;
	
	//使用produces将数据放入responseBody,设置其数据类型和字符集
	@RequestMapping(value="/userJson",produces="application/json; charset=UTF-8")
	public User userJson(){
		return user;
	}
	
	//使用produces将数据放入responseBody,设置其数据类型和字符集
	@RequestMapping(value="/userXml",produces=MimeTypeUtils.APPLICATION_XML_VALUE)
	public User userXml(){
		return user;
	}
	
	public static void main(String[] args){
//		SpringApplication.run(Chapter5Application.class, args);
		SpringApplication act=new SpringApplication(Chapter5Application.class);
		//启动Spring Boot应用
		act.run(args);
	}
	
}
