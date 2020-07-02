package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.Product;
import org.javaworld.cmsbackend.model.Response;

public interface ProductService {

	public List<Product> getAllProducts();

	public List<Product> getProducts(String name, int categoryId, int pageNumber, int pageSize);

	public Product findById(int id);

	public Product save(Product product);

	public Product update(Product product);

	public Response deleteById(int id);

	public Response deleteProductImage(String imageName);

}