package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Integer> {

}
