package com.tlh.springboot.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
	
	@Test
	public void encode(){
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}

}
