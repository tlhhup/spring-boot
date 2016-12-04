package com.tlh.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tlh.springboot.entity.User;
import com.tlh.springboot.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/userInfos")
	public List<User> findUserInfos(){
		return userService.findAll();
	}
	
}
