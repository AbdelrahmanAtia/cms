package org.javaworld.cmsbackend.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import org.springframework.http.MediaType;
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
	public Category findById(int id) {
		Optional<Category> result = categoryRepository.findById(id);
		Category theCategory = null;
		if (result.isPresent()) {
			theCategory = result.get();
		} else {
			// we didn't find the employee
			throw new RuntimeException("Did not find a category with id  " + id);
		}
		return theCategory;
	}

	@Override
	@Transactional
	public Category save(Category category) {
		
		category.setId(0); // force creating a new entity
		
		String base64Image = category.getBase64Image();
		String uniqueImageName = null;
		
		if (base64Image != null) {
			// generate unique file name
			uniqueImageName = FileUtil.getUniqueFileName() + ".jpg";
			category.setImageName(uniqueImageName);
		} else {
			category.setImageName(Constants.NOT_FOUND_IMAGE_NAME);
		}
		
		Category savedCategory = categoryRepository.save(category);

		// save image to file system
		if (uniqueImageName != null) {
			String path = createImagePath(uniqueImageName);
			boolean saved = FileUtil.saveImageToFileSystem(path, base64Image);
			if (!saved) {
				throw new RuntimeException("failed to save the image to file system");
			}
		}

		return savedCategory;
	}

	@Override
	public Category update(Category category) {
		Category tempCategory = categoryRepository.findById(category.getId()).get();

		if (category.getBase64Image() == null) {
			// keep the old image
			category.setImageName(tempCategory.getImageName());
		} else {

			// save the new image to the file system and update image name in data base
			String uniqueImageName = FileUtil.getUniqueFileName() + ".jpg";
			category.setImageName(uniqueImageName);

			String path = createImagePath(uniqueImageName);
			boolean saved = FileUtil.saveImageToFileSystem(path, category.getBase64Image());
			if (!saved) {
				throw new RuntimeException("failed to save the image to the file system");
			}

			// delete old image from file system except no-image.png
			if (!tempCategory.getImageName().equals(Constants.NOT_FOUND_IMAGE_NAME)) {
				String oldImagePath = createImagePath(tempCategory.getImageName());
				boolean deleted = FileUtil.deleteImageFromFileSystem(oldImagePath);
				if (!deleted) {
					throw new RuntimeException("failed to delete image from file system");
				}
			}
		}

		return categoryRepository.save(category);
	}

	@Override
	@Transactional
	public Response deleteById(int categoryId) {

		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		Category category = optionalCategory.get();
		if (category == null) {
			throw new RuntimeException("category with id " + categoryId + " not found");
		}		
		
		categoryRepository.deleteById(categoryId);

		// remove the image [if exists] from the file system
		String categoryImageName = category.getImageName();
		if(!categoryImageName.equals(Constants.NOT_FOUND_IMAGE_NAME)) {
			String imagePath = createImagePath(categoryImageName);
			boolean deleted = FileUtil.deleteImageFromFileSystem(imagePath);
			if (!deleted) {
				throw new RuntimeException("failed to delete the image from the file system");
			}
		}
		
		return new Response(true, "Deleted the category with id  " + categoryId);
	}

	@Override
	@Transactional
	public Response deleteCategoryImage(String imageName) {
		int rowsAffected = categoryRepository.deleteCategoryImage(imageName);
		if (rowsAffected == 0) {
			return new Response(false, "no image found with name " + imageName);
		}

		// remove the image from the file system
		String imagePath = createImagePath(imageName);
		boolean deleted = FileUtil.deleteImageFromFileSystem(imagePath);
		if (!deleted) {
			throw new RuntimeException("failed to delete the image from the file system");
		}

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
		return cmsBackEndApplication.getProjectFilesLocation() + File.separator + "categories_images" + File.separator
				+ imageName;
	}

}
