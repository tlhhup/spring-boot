package com.tlh.springboot.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tlh.springboot.entity.User;

public interface UserService {

	List<User> findAll();
	
	Page<User> findUserWithPage(User user,Pageable pageable);
	
	Map<String, Object> deleteUserById(int id);
	
	Map<String, Object> updateUser(User user);
	
	Map<String, Object> saveUser(User user);
	
}
