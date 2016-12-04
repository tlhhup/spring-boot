package com.tlh.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tlh.springboot.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
