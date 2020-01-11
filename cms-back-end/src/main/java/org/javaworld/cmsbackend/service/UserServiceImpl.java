package org.javaworld.cmsbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.javaworld.cmsbackend.constants.Constants;
import org.javaworld.cmsbackend.dao.UserRepository;
import org.javaworld.cmsbackend.entity.User;
import org.javaworld.cmsbackend.model.Response;
import org.javaworld.cmsbackend.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	HttpServletResponse httpServletResponse;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@Override
	public List<User> getUsers(String searchTerm, int pageNumber, int pageSize) {
		searchTerm = searchTerm.trim();

		Page<User> page = null;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		if (searchTerm.length() == 0) {
			page = userRepository.findAll(pageable);
		} else {
			page = userRepository.findByNameIgnoreCaseContainingOrEmailIgnoreCaseContaining(searchTerm, searchTerm, pageable);
		}

		httpServletResponse.addIntHeader("totalPages", page.getTotalPages());
		return page.hasContent() ? page.getContent() : new ArrayList<User>();
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
	
	@Override
	@Transactional
	public Response deleteById(int userId) {
		try {
			userRepository.deleteById(userId);
		} catch (EmptyResultDataAccessException ex) {
			return new Response(Constants.NOT_FOUND_STATUS, "user id not found - " + userId);
		}
		return new Response(Constants.OK_STATUS, "Deleted user id - " + userId);
	}

}
