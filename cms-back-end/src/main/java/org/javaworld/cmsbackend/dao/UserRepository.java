package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

	Page<User> findAll(Pageable pageable);

	Page<User> findByNameIgnoreCaseContainingOrEmailIgnoreCaseContaining(String name, String email, Pageable pageable);
	
	User findByEmail(String email);

}
