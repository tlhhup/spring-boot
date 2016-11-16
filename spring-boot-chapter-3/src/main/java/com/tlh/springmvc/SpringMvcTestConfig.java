package com.tlh.springmvc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
//方法的静态导入
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tlh.springmvc.config.SpringMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringMvcConfig.class})//指定springmvc的配置文件
@WebAppConfiguration("src/main/resource/views")//指定页面资源
public class SpringMvcTestConfig {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext act;
	
	@Autowired
	private MockHttpSession session;
	
	@Autowired
	private MockHttpServletRequest request;
	
	@Before
	public void steup(){
		this.mockMvc=MockMvcBuilders.webAppContextSetup(act).build();
	}
	
	@Test
	public void tesIndex() throws Exception{
		//模拟action的执行流程
		mockMvc.perform(get("/index"))//发送get请求
				.andExpect(status().isOk())//预期状态码
				.andExpect(view().name("index"))//预期的视图名
				.andExpect(forwardedUrl("/WEB-INF/classes/views/index.jsp"))//预期页面转向的真正路径
				.andExpect(model().attribute("user", "hello"));//预期model里的数据
	}
	
}
