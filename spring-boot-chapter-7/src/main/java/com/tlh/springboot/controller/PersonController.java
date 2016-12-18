package com.tlh.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tlh.springboot.entity.Person;
import com.tlh.springboot.service.PersonService;

@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping("/findAll")
	public List<Person> findAll(){
		return this.personService.findAll();
	}
	
	@RequestMapping("/findPersonInfo")
	public Person findPersonInfo(Person person){
		return this.personService.findPerson(person);
	}
	
	@RequestMapping("/deletePerson/{id}")
	public Map<String,Object> deletePerson(int id){
		this.personService.deletePerson(id);
		Map<String, Object> result=new HashMap<>();
		result.put("msg", "操作成功");
		return result;
	}
	
	@RequestMapping("/savePerson")
	public Map<String,Object> savePerson(Person person){
		this.personService.savePerson(person);
		Map<String, Object> result=new HashMap<>();
		result.put("msg", "操作成功");
		return result;
	}

}
