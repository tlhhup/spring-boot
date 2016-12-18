package com.tlh.springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping("/findUserWithPage")
	public Page<User> findUserWithPage(User user,Pageable pageable){
		return userService.findUserWithPage(user, pageable);
	}
	
	@RequestMapping("/deleteUser/{id}")
	public Map<String,Object> deleteUser(@PathVariable("id") int id){
		return null;
	}
	
	@RequestMapping("/saveUser")
	public Map<String,Object> saveUser(User user){
		return userService.saveUser(user);
	}
	@RequestMapping("/updateUser")
	public Map<String,Object> updateUser(User user){
		return userService.updateUser(user);
	}
	
	
}
