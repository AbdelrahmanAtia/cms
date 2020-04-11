package org.javaworld.cmsbackend.service;

import java.io.IOException;
import java.util.List;

import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.model.Response;

public interface CategoryService {

	public List<Category> findAll();

	public List<Category> getCategories(String searchTerm, int pageNumber, int pageSize);

	public Category findById(int id);

	public Category save(Category category);

	public Category update(Category category);

	public Response deleteById(int id);

	public void getCategoryImage(String imageName) throws IOException;

	public Response deleteCategoryImage(String imageName);

	boolean isUniqueCategoryName(String categoryName, int categoryId);

}