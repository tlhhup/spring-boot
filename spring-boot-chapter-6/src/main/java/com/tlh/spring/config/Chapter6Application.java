package com.tlh.spring.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages={"com.tlh.spring.**"})
public class Chapter6Application {

	public static void main(String[] args){
		SpringApplication.run(Chapter6Application.class, args);
	}
	
	@Bean
	public TomcatEmbeddedServletContainerFactory servletContainerFactory(){
		TomcatEmbeddedServletContainerFactory factory=new TomcatEmbeddedServletContainerFactory(){
			
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint constraint=new SecurityConstraint();
				constraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection=new SecurityCollection();
				collection.addPattern("/*");
				constraint.addCollection(collection);
				context.addConstraint(constraint);
			}
			
		};
		factory.addAdditionalTomcatConnectors(connector());
		return factory;
	}

	@Bean
	public Connector connector() {
		//设置http协议时的信息
		Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		//使用http联系是使用的端口
		connector.setPort(8080);
		connector.setSecure(false);
		//重定向到https使用的端口及此时server.port的值
		connector.setRedirectPort(8089);
		return connector;
	}
	
}
