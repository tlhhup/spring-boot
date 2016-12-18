package com.tlh.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.tlh.springboot.repository.custom.CustomJpaRepositoryFactoryBean;

@SpringBootApplication()
@EnableJpaRepositories(repositoryFactoryBeanClass=CustomJpaRepositoryFactoryBean.class)
public class Chapter7Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter7Application.class, args);
	}

}
