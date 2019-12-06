package org.javaworld.cmsbackend.controller;

import java.util.List;

import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.model.Response;
import org.javaworld.cmsbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories/all")
	public List<Category> getAllCategories() {
		return categoryService.findAll();
	}

	@GetMapping("/categories")
	public List<Category> getCategories(@RequestParam String searchTerm, @RequestParam int pageNumber,
			@RequestParam int pageSize) {
		return categoryService.getCategories(searchTerm, pageNumber, pageSize);
	}

	@GetMapping("/categories/{categoryId}")
	public Category getCategory(@PathVariable int categoryId) {
		return categoryService.findById(categoryId);
	}

	@PostMapping("/categories")
	public Category addCategory(@RequestBody Category category) {
		category.setId(0); // force creating a new entity
		categoryService.save(category);
		return category;
	}

	@PutMapping("/categories")
	public Category updateCategory(@RequestBody Category category) {
		categoryService.save(category);
		return category;
	}

	@DeleteMapping("/categories/{categoryId}")
	public Response deleteCategory(@PathVariable int categoryId) {
		return categoryService.deleteById(categoryId);
	}

}
