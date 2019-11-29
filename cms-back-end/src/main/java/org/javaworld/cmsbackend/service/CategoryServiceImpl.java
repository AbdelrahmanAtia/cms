package org.javaworld.cmsbackend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.javaworld.cmsbackend.constants.Constants;
import org.javaworld.cmsbackend.dao.CategoryRepository;
import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findById(int id) {
		Optional<Category> result = categoryRepository.findById(id);
		Category theCategory = null;
		if (result.isPresent()) {
			theCategory = result.get();
		} else {
			// we didn't find the employee
			throw new RuntimeException("Did not find category id - " + id);
		}
		return theCategory;
	}

	@Override
	public void save(Category category) {
		categoryRepository.save(category);
	}

	
	@Override
	@Transactional
	public Response deleteById(int categoryId) {
		try {
			categoryRepository.deleteById(categoryId);
		} catch (EmptyResultDataAccessException ex) {
			return new Response(Constants.NOT_FOUND_STATUS, "category id not found - " + categoryId);
		}
		return new Response(Constants.OK_STATUS, "Deleted category id - " + categoryId);
	}
		
}
