package com.tlh.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.tlh.springboot.service.impl.SysUserServiceImpl;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	UserDetailsService customUserDetailsService() {
		return new SysUserServiceImpl();
	}
	
	//配置用户认证信息
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()//开启授权
			.anyRequest().authenticated()//所有的请求都必须先授权
			.and()//
			.formLogin()//登录
				.loginPage("/login")//登录页面
				.failureUrl("/login?error")//失败的地址
				.permitAll()//用户任意访问
			.and()//
			.logout()//
				.permitAll();
	}

}
