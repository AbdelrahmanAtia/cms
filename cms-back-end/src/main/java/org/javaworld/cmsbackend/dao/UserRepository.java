package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

	Page<User> findAll(Pageable pageable);

	User findByEmail(String email);

	Page<User> findByActive(boolean active, Pageable pageable);

	Page<User> findByNameIgnoreCaseContainingOrEmailIgnoreCaseContaining(String name, String email, Pageable pageable);

	Page<User> findByActiveAndNameIgnoreCaseContainingOrActiveAndEmailIgnoreCaseContaining(boolean active1, String name, boolean active2, String email, Pageable pageable);
}
