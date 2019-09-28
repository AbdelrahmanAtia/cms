package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.Product;

public interface ProductService {

	public List<Product> findAll();

	public Product findById(int id);
	
	public List<Product> findByNameIgnoreCaseContaining(String name);

	public void save(Product product);

	public void deleteById(int id);

}