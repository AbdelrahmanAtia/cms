package org.javaworld.cmsbackend.service;

import java.util.List;

import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.model.Response;

public interface CategoryService {

	public List<Category> findAll();

	public Category findById(int id);

	public void save(Category category);

	public Response deleteById(int id);

}