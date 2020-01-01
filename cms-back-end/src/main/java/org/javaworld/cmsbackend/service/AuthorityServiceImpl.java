package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.dao.AuthorityRepository;
import org.javaworld.cmsbackend.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public List<Authority> findAll() {
		return authorityRepository.findAll();
	}

}
