package com.tlh.spring.dao;

import org.springframework.data.repository.CrudRepository;

import com.tlh.spring.entity.Customer;

public interface CustomerDao extends CrudRepository<Customer, Long> {

}
