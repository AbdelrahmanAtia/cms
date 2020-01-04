package org.javaworld.cmsbackend.service;

import java.util.List;
import java.util.Optional;

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
	public User findById(int userId) {
		Optional<User> result = userRepository.findById(userId);
		User user = null;
		if (result.isPresent())
			user = result.get();
		else
			throw new RuntimeException("Did not find user with id - " + userId);
		return user;
	}

	@Override
	public void save(User user) {
		user.setId(0); // force creating a new entity
		String registerDate = DateUtil.getCurrentDate("dd-MM-yyyy, HH:mm:ss");
		user.setRegisterDate(registerDate);
		userRepository.save(user);
	}

	@Override
	public void update(User user) {
		String registerDate = this.findById(user.getId()).getRegisterDate();
		user.setRegisterDate(registerDate);
		userRepository.save(user);
	}

}
