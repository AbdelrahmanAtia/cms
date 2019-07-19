package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.Category;

public interface CategoryService {

	public List<Category> findAll();

	public Category findById(int id);

	public void save(Category category);

	public void deleteById(int id);

}