package com.tlh.spring.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages="com.tlh.spring.**")
@EnableTransactionManagement
public class SpringConfig {
	
	//配置数据源
	@Bean
	public DataSource dataSource(){
		EmbeddedDatabaseBuilder builder=new EmbeddedDatabaseBuilder();
		//使用内嵌的HSQL数据库
		return builder.setType(EmbeddedDatabaseType.HSQL).build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		//创建hibernate的jpa的适配器
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.HSQL);
		//生成ddl
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		//设置jpa的实现适配器
		factory.setJpaVendorAdapter(vendorAdapter);
		//设置扫描的包及jpa的包(使用了jpa注解的包)
		factory.setPackagesToScan("com.tlh.spring.entity");
		//设置数据源
		factory.setDataSource(dataSource());

		return factory;
	}
	
	//配置事务管理器
	@Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}

}
