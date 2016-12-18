package com.tlh.springboot.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tlh.springboot.entity.User;
import com.tlh.springboot.repository.custom.CustomJpaRepsitory;

public interface UserRepository extends CustomJpaRepsitory<User, Integer>{
	
	@Modifying
	@Query("update User set userName=:userName,address=:address,sex=:sex where id=:id")
	int updateUser(User user);

}
