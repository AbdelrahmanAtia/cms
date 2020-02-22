package org.javaworld.cmsbackend.controller;

import org.javaworld.cmsbackend.model.CustomResponse;
import org.javaworld.cmsbackend.model.LoginInfo;
import org.javaworld.cmsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationRestController {

	@Autowired
	private UserService userService;

	@Autowired
	CustomResponse customResponse;

	
	@PostMapping("/authentication/login")
	public UserDetails login(@RequestBody LoginInfo loginInfo) {
		String email = loginInfo.getEmail();
		String password = loginInfo.getPassword();
		UserDetails userDetails = userService.loadUserByEmail(email);
		System.out.println("userDetails = " + userDetails);
		if (userDetails != null && userDetails.getPassword().equals(password)) {
			return userDetails;
		} else {
			throw new BadCredentialsException("invalid email or password");
		}
	}

	
	
	/*
	@PostMapping("/authentication/login")
	public CustomResponse login(@RequestBody LoginInfo loginInfo) {
		String email = loginInfo.getEmail();
		String password = loginInfo.getPassword();
		UserDetails userDetails = userService.loadUserByEmail(email);
		System.out.println("userDetails = " + userDetails);
		if (userDetails != null && userDetails.getPassword().equals(password)) {
			customResponse.setStatus(true);
			customResponse.setDescription("user logged in successfully");
			customResponse.setResponseDetails("userDetails", userDetails);
		} else {
			customResponse.setStatus(false);
			customResponse.setDescription("invalid email or password");
		}
		return customResponse;
	}
	*/

}
