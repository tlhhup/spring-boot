package com.tlh.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/endPoints").withSockJS();//注册一个stomp的endPoint并指定使用SockJs协议
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//设置订阅信息请求的前缀
		registry.enableSimpleBroker("/topic");
	}

}
