package com.tlh.spring.condition;

import org.springframework.stereotype.Service;

@Service
public class LinuxListService implements ListService {

	@Override
	public String showListCmd() {
		return "ls";
	}

}
