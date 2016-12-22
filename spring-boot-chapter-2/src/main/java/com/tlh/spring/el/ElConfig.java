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
 * spring�ı��ʽ��Ӧ��
 */
@Configuration//��Ǹ�Ϊspring��������
@ComponentScan("com.tlh.spring.el")//����ɨ��İ�
@PropertySource("classpath:jdbc.properties")//ָ����Դ�ļ�
public class ElConfig {

	//����ֵ
	@Value("hello,world")
	private String normal;
	
	//ȥ�����ļ��е�����
	@Value("${jdbc.url}")
	private String url;
	
	//ע�����ϵͳ������
	@Value("#{systemProperties['os.name']}")
	private String osName;
	
	//ע����ʽ�Ľ��
	@Value("#{T(java.lang.Math).random()*100.0}")
	private double randomNumber;
	
	//ע������bean������
	@Value("#{userService.name}")
	private String name;
	
	//ע���ļ�����
	@Value("classpath:jdbc.properties")
	private Resource resource;
	
	//ע����ַ����
	@Value("http://www.baidu.com")
	private Resource testUrl;
	
	//ע�������ļ�
	@Autowired
	private Environment environment;
	
	//����bean��ע�������ļ�(��Ϊע�������ļ����÷�������Ϊ��̬��),
	@Bean//�����ڷ����ϣ���ʹ�÷�����������Ϊbean������
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	public void outputResource() throws Exception{
		System.out.println("normal��"+normal);
		System.out.println("url��"+url);
		System.out.println("osName��"+osName);
		System.out.println("resource��"+IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8")));
		System.out.println("testUrl��"+IOUtils.toString(testUrl.getInputStream(), Charset.forName("UTF-8")));
		System.out.println("environment��"+environment.getProperty("c3p0.pool.size.max"));
	}
	
}
