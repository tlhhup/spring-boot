package com.tlh.spring.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.tlh.spring.entity.WebSocketMessage;

@Controller
public class WebSocketController {

	@SendTo("/topic/boardcast")//订阅地址：服务器--->客户端
	@MessageMapping("/welcome")//请求的地址：客户端-->服务器
	public WebSocketMessage message(WebSocketMessage message){
		WebSocketMessage result=new WebSocketMessage();
		result.setName("Welcome，"+message.getName());
		
		return result;
	}
	
}
