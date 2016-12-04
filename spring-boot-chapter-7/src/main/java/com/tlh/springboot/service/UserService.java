package com.tlh.springboot.service;

import java.util.List;

import com.tlh.springboot.entity.User;

public interface UserService {

	List<User> findAll();
	
}
