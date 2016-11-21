package com.tlh.spring.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tlh.spring.config.SpringConfig;
import com.tlh.spring.dao.CustomerDao;
import com.tlh.spring.entity.Customer;

public class CustomerDaoTest {

	@Test
	public void saveCustomer(){
		AnnotationConfigApplicationContext act=new AnnotationConfigApplicationContext();
		act.register(SpringConfig.class);
		act.refresh();
		
		CustomerDao customerDao = act.getBean(CustomerDao.class);
		Customer customer=new Customer();
		customer.setName("张三");
		customer.setAddress("成都");
		
		customerDao.save(customer);
		
		Iterable<Customer> customers = customerDao.findAll();
		customers.forEach((customera)->System.out.println(customera.getName()));
		
		act.close();
	}
	
	@Test
	public void findCustomer(){
		AnnotationConfigApplicationContext act=new AnnotationConfigApplicationContext();
		act.register(SpringConfig.class);
		act.refresh();
		
		CustomerDao customerDao = act.getBean(CustomerDao.class);
		Iterable<Customer> customers = customerDao.findAll();
		customers.forEach((customer)->System.out.println(customer.getName()));
		
		act.close();
	}
	
}
