package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}