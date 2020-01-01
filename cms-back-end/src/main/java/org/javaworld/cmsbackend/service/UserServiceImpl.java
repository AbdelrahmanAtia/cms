package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.dao.UserRepository;
import org.javaworld.cmsbackend.entity.User;
import org.javaworld.cmsbackend.util.DateUtil;
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

	@Override
	public void save(User user) {
		user.setId(0); // force creating a new entity
		String registerDate = DateUtil.getCurrentDate("dd-MM-yyyy, HH:mm");
		user.setRegisterDate(registerDate);
		userRepository.save(user);
	}

}
