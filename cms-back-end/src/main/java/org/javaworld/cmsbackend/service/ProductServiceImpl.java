package org.javaworld.cmsbackend.service;

import java.util.List;
import java.util.Optional;

import org.javaworld.cmsbackend.dao.ProductRepository;
import org.javaworld.cmsbackend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired 
	private ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findById(int id) {
		Optional <Product>result = productRepository.findById(id);
		Product product = null;
		if(result.isPresent()) 
			product =  result.get();
		else 
			throw new RuntimeException("Did not find product id - " + id);
		return product;
	}

	@Override
	public void save(Product product) {
		productRepository.save(product);		
	}

	@Override
	public void deleteById(int id) {		
		productRepository.deleteById(id);
	}

}
