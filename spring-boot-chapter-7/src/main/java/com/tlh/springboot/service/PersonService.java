package com.tlh.springboot.service;

import java.util.List;

import com.tlh.springboot.entity.Person;

public interface PersonService {

	List<Person> findAll();
	
	void deletePerson(int id);
	
	Person findPerson(Person person);
	
	void savePerson(Person person);
	
}
