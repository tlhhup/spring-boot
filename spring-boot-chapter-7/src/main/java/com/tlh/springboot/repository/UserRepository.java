package com.tlh.springboot.repository;

import com.tlh.springboot.entity.User;
import com.tlh.springboot.repository.custom.CustomJpaRepsitory;

public interface UserRepository extends CustomJpaRepsitory<User, Integer>{

}
