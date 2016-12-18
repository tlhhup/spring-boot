package com.tlh.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tlh.springboot.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{

}
