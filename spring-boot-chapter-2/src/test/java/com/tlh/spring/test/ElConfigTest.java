package com.tlh.spring.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tlh.spring.el.ElConfig;

public class ElConfigTest {

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext act =new AnnotationConfigApplicationContext();
		//ע��������
		act.register(ElConfig.class);
		//ˢ������spring����
		act.refresh();
		
		ElConfig elConfig = act.getBean(ElConfig.class);
		elConfig.outputResource();
		
		String[] names = act.getBeanDefinitionNames();
		for(String name:names){
			System.out.println(name);
		}
		
		act.close();
	}

}
