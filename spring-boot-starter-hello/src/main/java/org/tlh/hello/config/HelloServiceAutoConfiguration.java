package org.tlh.hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tlh.hello.service.HelloService;

@Configuration
@EnableConfigurationProperties(HelloServiceProperties.class)
@ConditionalOnClass(HelloService.class)//如果该类在类路径中存在
@ConditionalOnProperty(prefix="tlh.hello",value="enabled",matchIfMissing=true)//检测指定的属性是否设置了指定的值
public class HelloServiceAutoConfiguration {

	@Autowired
	private HelloServiceProperties helloServiceProperties;
	
	@Bean
	@ConditionalOnMissingBean(HelloService.class)//如果该bean不存在则创建
	public HelloService helloService(){
		HelloService helloService=new HelloService();
		helloService.setMessage(helloServiceProperties.getMessage());
		return helloService;
	}
	
}
