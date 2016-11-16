package com.tlh.springmvc.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext act=new AnnotationConfigWebApplicationContext();
		act.register(SpringMvcConfig.class);
		//��servlet����
		act.setServletContext(servletContext);
		
		//����springmvc�Ŀ�����
		Dynamic dynamic = servletContext.addServlet("springmvc", new DispatcherServlet(act));
		//���ú�׺
		dynamic.addMapping("/");
		dynamic.setLoadOnStartup(1);
	}

}
