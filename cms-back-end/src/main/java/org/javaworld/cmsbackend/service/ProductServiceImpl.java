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
	public List<Product> getProducts(int categoryId, int pageNumber, int pageSize) {

		Category category = new Category(categoryId);
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Product> page = categoryId == 0 ? productRepository.findAll(pageable)
				: productRepository.findByCategory(category, pageable);

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
	public void save(Product product) {
		productRepository.save(product);
	}

	@Override
	public void deleteById(int id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> findByNameIgnoreCaseContaining(String name) {
		return productRepository.findByNameIgnoreCaseContaining(name);
	}

}
