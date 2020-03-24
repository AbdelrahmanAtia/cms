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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	public List<User> getUsers(String searchTerm, int pageNumber, int pageSize, String status) {
		searchTerm = searchTerm.trim();

		Page<User> page = null;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		if (searchTerm.length() == 0) {
			if(status.equals("All")) {
				page = userRepository.findAll(pageable);	
			} else if(status.equals("Active")) {
				page = userRepository.findByActive(true, pageable);
			} else if(status.equals("Inactive")) {
				page = userRepository.findByActive(false, pageable);
			} else {
				throw new RuntimeException("status: " + status + " is invalid!!");
			}
		} else {
			if(status.equals("All")) {
				page = userRepository.findByNameIgnoreCaseContainingOrEmailIgnoreCaseContaining(searchTerm, searchTerm, pageable);
			} else if(status.equals("Active")) {
				page = userRepository.findByActiveAndNameIgnoreCaseContainingOrActiveAndEmailIgnoreCaseContaining(true, searchTerm, true, searchTerm, pageable);
			} else if(status.equals("Inactive")) {
				page = userRepository.findByActiveAndNameIgnoreCaseContainingOrActiveAndEmailIgnoreCaseContaining(false, searchTerm, false, searchTerm, pageable);
			} else {
				throw new RuntimeException("status: " + status + " is invalid!!");
			}			
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
	public User save(User user) {
		user.setId(0); // force creating a new entity
		if(!isUniqueEmail(user.getEmail(), 0)) {
			throw new RuntimeException("email already exists");
		}
		String registerDate = DateUtil.getCurrentDate("dd-MM-yyyy, HH:mm:ss");
		user.setRegisterDate(registerDate);
		return userRepository.save(user);
	}

	@Override
	public User update(User user) {
		if(!isUniqueEmail(user.getEmail(), user.getId())) {
			throw new RuntimeException("email already exists");
		}
		String registerDate = this.findById(user.getId()).getRegisterDate();
		user.setRegisterDate(registerDate);
		return userRepository.save(user);
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
	
	@Override
	public boolean isUniqueEmail(String email, int userId) {
		User user = userRepository.findByEmail(email);
		if (user == null || user.getId() == userId)
			return true;
		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByName(username);
	}
	
	@Override
	public UserDetails loadUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
