package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.model.Response;

public interface CategoryService {

	List<Category> findAll();

	List<Category> getCategories(String searchTerm, int pageNumber, int pageSize);

	Category getCategoryById(int categoryId);

	Category saveCategory(Category category);

	Category updateCategory(Category category);

	Response deleteCategoryById(int id);

	Response deleteCategoryImage(String imageName);

	boolean isUniqueCategoryName(String categoryName, int categoryId);

}