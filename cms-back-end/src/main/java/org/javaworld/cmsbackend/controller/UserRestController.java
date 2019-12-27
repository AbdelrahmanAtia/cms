package org.javaworld.cmsbackend.controller;

import java.util.List;

import org.javaworld.cmsbackend.entity.User;
import org.javaworld.cmsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping("/users/all")
	public List<User> getAllUsers() {
		return userService.findAll();
	}

}
