package com.tlh.springboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tlh.springboot.entity.User;
import com.tlh.springboot.repository.UserRepository;
import com.tlh.springboot.service.UserService;

@Service("userService")
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Page<User> findUserWithPage(User user, Pageable pageable) {
		return userRepository.findByAuto(user, pageable);
	}

	@Override
	public Map<String, Object> deleteUserById(int id) {
		Map<String, Object> result=new HashMap<>();
		try {
			userRepository.delete(id);
			result.put("flag", true);
			result.put("msg", "删除成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("flag", false);
		result.put("msg", "删除失败");
		return result;
	}

	@Override
	public Map<String, Object> updateUser(User user) {
		Map<String, Object> result=new HashMap<>();
		try {
			userRepository.updateUser(user);
			result.put("flag", true);
			result.put("msg", "删除成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("flag", false);
		result.put("msg", "删除失败");
		return result;
	}

	@Override
	public Map<String, Object> saveUser(User user) {
		Map<String, Object> result=new HashMap<>();
		try {
			this.userRepository.save(user);//该内部会根据id是否存在调用persist和merger方法
			result.put("flag", true);
			result.put("msg", "删除成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("flag", false);
		result.put("msg", "删除失败");
		return result;
	}

}
