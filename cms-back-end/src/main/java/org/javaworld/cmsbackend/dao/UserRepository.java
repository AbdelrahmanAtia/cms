package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
