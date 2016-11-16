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
//�����ľ�̬����
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tlh.springmvc.config.SpringMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringMvcConfig.class})//ָ��springmvc�������ļ�
@WebAppConfiguration("src/main/resource/views")//ָ��ҳ����Դ
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
		//ģ��action��ִ������
		mockMvc.perform(get("/index"))//����get����
				.andExpect(status().isOk())//Ԥ��״̬��
				.andExpect(view().name("index"))//Ԥ�ڵ���ͼ��
				.andExpect(forwardedUrl("/WEB-INF/classes/views/index.jsp"))//Ԥ��ҳ��ת�������·��
				.andExpect(model().attribute("user", "hello"));//Ԥ��model�������
	}
	
}
