package com.tlh.springboot.service;

import com.tlh.springboot.entity.SysUser;

public interface SysUserService {

	SysUser findSysUser(String userName);
	
}
