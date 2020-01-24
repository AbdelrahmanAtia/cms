package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.User;
import org.javaworld.cmsbackend.model.Response;

public interface UserService {

	public List<User> findAll();

	public List<User> getUsers(String searchTerm, int pageNumber, int pageSize, String status);

	public void save(User user);

	public User findById(int userId);

	public void update(User user);

	public Response deleteById(int userId);

	public boolean isUniqueEmail(String email, int userId);

}