package org.javaworld.cmsbackend.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.javaworld.cmsbackend.CmsBackEndApplication;
import org.javaworld.cmsbackend.constants.Constants;
import org.javaworld.cmsbackend.dao.CategoryRepository;
import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.model.Response;
import org.javaworld.cmsbackend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	CmsBackEndApplication cmsBackEndApplication;

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
	public Category getCategoryById(int categoryId) {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		return optionalCategory.orElseThrow(() -> {
			return new RuntimeException("Did not find a category with id  " + categoryId);
		});
	}

	@Override
	@Transactional
	public Category saveCategory(Category category) {

		// force creating a new entity
		category.setId(0);

		// set category image name
		String base64Image = category.getBase64Image();
		String categoryImageName = (base64Image == null) ? 
				Constants.NOT_FOUND_IMAGE_NAME : FileUtil.getUniqueFileName() + ".jpg";
		category.setImageName(categoryImageName);

		// save category details to the data base
		Category savedCategory = categoryRepository.save(category);

		// save category image to file system
		if (base64Image != null) {
			String path = createImagePath(savedCategory.getImageName());
			FileUtil.saveImageToFileSystem(path, base64Image);
		}

		return savedCategory;
	}

	@Override
	@Transactional
	public Category updateCategory(Category category) {
		
		//get old category details
		Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
		String oldImageName = optionalCategory.orElseThrow(() -> {
			return new RuntimeException("Did not find a category with id  " + category.getId());
		}).getImageName();		
		
		//set category image name  >> keep old image in case base64Image is null
		String categoryImageName = (category.getBase64Image() == null) ? 
				oldImageName : FileUtil.getUniqueFileName() + ".jpg";		
		category.setImageName(categoryImageName);
		
		//save category details to the data base
		Category updatedCategory = categoryRepository.save(category);

		//save category image to file system and delete old image
		if(category.getBase64Image() != null) {
			String newImagePath = createImagePath(updatedCategory.getImageName());
			FileUtil.saveImageToFileSystem(newImagePath, category.getBase64Image());
			
			if(!oldImageName.equals(Constants.NOT_FOUND_IMAGE_NAME)) {
				FileUtil.deleteImageFromFileSystem(createImagePath(oldImageName));
			}
		}
		
		return updatedCategory;
	}

	@Override
	@Transactional
	public Response deleteCategoryById(int categoryId) {

		//get category details
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		String categoryImageName = optionalCategory.orElseThrow(() -> {
			throw new RuntimeException("category with id " + categoryId + " not found");
		}).getImageName();
		
		//delete category details from the data base
		categoryRepository.deleteById(categoryId);

		// delete category image from the file system
		if(!categoryImageName.equals(Constants.NOT_FOUND_IMAGE_NAME)) {
			FileUtil.deleteImageFromFileSystem(createImagePath(categoryImageName));
		}
		
		return new Response(true, "Deleted the category with id  " + categoryId);
	}

	@Override
	@Transactional
	public Response deleteCategoryImage(String imageName) {
		
		//delete image from data base
		int rowsAffected = categoryRepository.deleteCategoryImage(imageName);
		if (rowsAffected == 0) {
			return new Response(false, "no image found with name " + imageName);
		}

		// delete the image from the file system
		FileUtil.deleteImageFromFileSystem(createImagePath(imageName));

		return new Response(true, "image deleted successfully");
	}

	@Override
	public boolean isUniqueCategoryName(String categoryName, int categoryId) {
		Category category = categoryRepository.findByName(categoryName);
		// the 2nd condition is for edit mode
		if (category == null || category.getId() == categoryId)
			return true;
		return false;
	}

	private String createImagePath(String imageName) {
		return cmsBackEndApplication.getProjectFilesLocation() 
				+ File.separator + "categories/categories_images" 
				+ File.separator + imageName;
	}

}
