package com.tlh.spring.el;

import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

/**
 * spring的表达式的应用
 */
@Configuration//标记该为spring的配置类
@ComponentScan("com.tlh.spring.el")//设置扫描的包
@PropertySource("classpath:jdbc.properties")//指定资源文件
public class ElConfig {

	//字面值
	@Value("hello,world")
	private String normal;
	
	//去属性文件中的属性
	@Value("${jdbc.url}")
	private String url;
	
	//注入操作系统的属性
	@Value("#{systemProperties['os.name']}")
	private String osName;
	
	//注入表达式的结果
	@Value("#{T(java.lang.Math).random()*100.0}")
	private double randomNumber;
	
	//注入其他bean的属性
	@Value("#{userService.name}")
	private String name;
	
	//注入文件内容
	@Value("classpath:jdbc.properties")
	private Resource resource;
	
	//注入网址内容
	@Value("http://www.baidu.com")
	private Resource testUrl;
	
	//注入属性文件
	@Autowired
	private Environment environment;
	
	//定义bean，注入属性文件(因为注入属性文件，该方法必须为静态的),
	@Bean//定义在方法上，将使用方法的名称作为bean的名称
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	public void outputResource() throws Exception{
		System.out.println("normal："+normal);
		System.out.println("url："+url);
		System.out.println("osName："+osName);
		System.out.println("resource："+IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8")));
		System.out.println("testUrl："+IOUtils.toString(testUrl.getInputStream(), Charset.forName("UTF-8")));
		System.out.println("environment："+environment.getProperty("c3p0.pool.size.max"));
	}
	
}
