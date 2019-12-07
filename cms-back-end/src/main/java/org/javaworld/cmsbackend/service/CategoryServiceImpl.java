package org.javaworld.cmsbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.javaworld.cmsbackend.constants.Constants;
import org.javaworld.cmsbackend.dao.CategoryRepository;
import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	HttpServletResponse httpServletResponse;

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	@Override
	public List<Category> getCategories(String name, int pageNumber, int pageSize) {
		name = name.trim();
				
		Page<Category> page = null;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);	
		
		if (name.length() == 0) {
			page = categoryRepository.findAll(pageable);
		} else {
			page = categoryRepository.findByNameIgnoreCaseContaining(name, pageable);
		}
		
		httpServletResponse.addIntHeader("totalPages", page.getTotalPages());		
		return page.hasContent() ? page.getContent() : new ArrayList<Category>();
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

	@Override
	@Transactional
	public Response deleteCategoryImage(int categoryId) {
		int rowsAffected = categoryRepository.deleteCategoryImage(categoryId);
		if (rowsAffected == 0)
			return new Response(Constants.NOT_FOUND_STATUS, "category id not found - " + categoryId);
		return new Response(Constants.OK_STATUS, "Deleted  image for category id - " + categoryId);
	}

		
}
