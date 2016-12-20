package com.tlh.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tlh.springboot.entity.SysUser;
import com.tlh.springboot.repository.SysUserRepository;
import com.tlh.springboot.service.SysUserService;

@Service
@Transactional
public class SysUserServiceImpl implements SysUserService,UserDetailsService {

	@Autowired
	private SysUserRepository sysUserRepository;
	
	@Override
	public SysUser findSysUser(String userName) {
		return sysUserRepository.findByUsername(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String usename) throws UsernameNotFoundException {
		return sysUserRepository.findByUsername(usename);
	}

}
