package com.tlh.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tlh.springboot.entity.Person;
import com.tlh.springboot.repository.PersonRepository;
import com.tlh.springboot.service.PersonService;

@Service
@Transactional
public class PersonServiceimpl implements PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	@Caching(cacheable={@Cacheable(value="persons")})
	@Override
	public List<Person> findAll() {
		return personRepository.findAll();
	}

	//删除persons缓存中key为id的缓存
	@CacheEvict(value="persons")
	@Override
	public void deletePerson(int id) {
		personRepository.delete(id);
	}

	@CachePut(value="persons",key="#person.id")
	@Override
	public Person findPerson(Person person) {
		return personRepository.findOne(person.getId());
	}

	@CachePut(value="persons",key="#person.id")
	@Override
	public void savePerson(Person person) {
		personRepository.save(person);
	}

}
