package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

}
