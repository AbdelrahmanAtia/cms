package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.dao.UserRepository;
import org.javaworld.cmsbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
