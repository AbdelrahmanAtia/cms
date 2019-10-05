package org.javaworld.cmsbackend.dao;

import java.util.List;

import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends JpaRepository<Product, Integer>, 
                                           PagingAndSortingRepository<Product, Integer> {

	Page<Product> findAll(Pageable pageable);
	
	Page<Product> findByCategory(Category category, Pageable pageable);
	
	List<Product> findByNameIgnoreCaseContaining(String name);	
	
}
