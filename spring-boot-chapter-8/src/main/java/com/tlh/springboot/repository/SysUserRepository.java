package com.tlh.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tlh.springboot.entity.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Integer> {

	/**
	 * 通过findBy关键字来通过属性进行查询
	 * @param userName
	 * @return
	 */
	SysUser findByUsername(String userName);
	
}
