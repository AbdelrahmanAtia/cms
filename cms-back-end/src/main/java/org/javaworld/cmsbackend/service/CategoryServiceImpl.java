package org.javaworld.cmsbackend.service;

import java.util.List;
import java.util.Optional;

import org.javaworld.cmsbackend.dao.CategoryRepository;
import org.javaworld.cmsbackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void deleteById(int id) {
		categoryRepository.deleteById(id);
	}
}
