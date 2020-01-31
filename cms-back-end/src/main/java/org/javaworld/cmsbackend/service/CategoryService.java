package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.model.Response;

public interface CategoryService {

	public List<Category> findAll();
	
	public List<Category> getCategories(String searchTerm, int pageNumber, int pageSize);

	public Category findById(int id);

	public void save(Category category);

	public Response deleteById(int id);
	
	public Response deleteCategoryImage(int categoryId);

	boolean isUniqueCategoryName(String categoryName, int categoryId);

}