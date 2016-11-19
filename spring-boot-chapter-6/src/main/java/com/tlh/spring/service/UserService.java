package com.tlh.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.tlh.spring.entity.User;

@Service
public class UserService {

	private static List<User> users;
	
	static{
		users=new ArrayList<>();
		User user=null;
		String[] addresses={"成都","北京","贵阳"};
		Random random=new Random();
		for(int i=0;i<10;i++){
			user=new User();
			user.setId(i+1);
			user.setAge(random.nextInt(35));
			user.setUserName("第"+(i+1)+"个元素");
			user.setAddress(addresses[random.nextInt(addresses.length)]);
			users.add(user);
			user=null;
		}
	}
	
	public List<User> findUser(){
		return users;
	}
	
}
