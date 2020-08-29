package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.Product;
import org.javaworld.cmsbackend.model.Response;

public interface ProductService {

	public List<Product> getAllProducts();

	public List<Product> getProducts(String name, int categoryId, int pageNumber, int pageSize);

	public Product getProductById(int id);

	public Product saveProduct(Product product);

	public Product updateProduct(Product product);

	public Response deleteProductById(int id);

	public Response deleteProductImage(String imageName);

}