package org.javaworld.cmsbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.javaworld.cmsbackend.dao.ProductRepository;
import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	HttpServletResponse httpServletResponse;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProducts(String name, int categoryId, int pageNumber, int pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Category category = new Category(categoryId);
		name = name.trim();
		Page<Product> page = null;

		if (categoryId == 0) {

			if (name.length() == 0)
				page = productRepository.findAll(pageable);
			else
				page = productRepository.findByNameIgnoreCaseContaining(name, pageable);

		} else {

			if (name.length() == 0)
				page = productRepository.findByCategory(category, pageable);
			else
				page = productRepository.findByCategoryAndNameIgnoreCaseContaining(category, name, pageable);

		}

		httpServletResponse.addIntHeader("totalPages", page.getTotalPages());

		return page.hasContent() ? page.getContent() : new ArrayList<Product>();

	}

	@Override
	public Product findById(int id) {
		Optional<Product> result = productRepository.findById(id);
		Product product = null;
		if (result.isPresent())
			product = result.get();
		else
			throw new RuntimeException("Did not find product id - " + id);
		return product;
	}

	@Override
	public Product save(Product product) {
		product.setId(0); // force creating a new entity
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void deleteById(int id) {
		productRepository.deleteById(id);
	}

}
