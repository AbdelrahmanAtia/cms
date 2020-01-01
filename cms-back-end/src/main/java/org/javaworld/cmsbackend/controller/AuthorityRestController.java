package org.javaworld.cmsbackend.controller;

import java.util.List;

import org.javaworld.cmsbackend.entity.Authority;
import org.javaworld.cmsbackend.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthorityRestController {

	@Autowired
	private AuthorityService authorityService;
	
	@GetMapping("/authorities/all")
	public List<Authority> getAuthorities() {
		return authorityService.findAll();
	}
}