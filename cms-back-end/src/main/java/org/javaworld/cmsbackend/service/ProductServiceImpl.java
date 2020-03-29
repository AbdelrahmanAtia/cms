package org.javaworld.cmsbackend.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.javaworld.cmsbackend.CmsBackEndApplication;
import org.javaworld.cmsbackend.constants.Constants;
import org.javaworld.cmsbackend.dao.ProductRepository;
import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.entity.Product;
import org.javaworld.cmsbackend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	HttpServletResponse httpServletResponse;
	
	@Autowired
	CmsBackEndApplication cmsBackEndApplication;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProducts(String name, int categoryId, int pageNumber, int pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Category category = new Category(categoryId);
		name = name.trim();
		Page<Product> page = null;

		if (categoryId == 0) {

			if (name.length() == 0)
				page = productRepository.findAll(pageable);
			else
				page = productRepository.findByNameIgnoreCaseContaining(name, pageable);

		} else {

			if (name.length() == 0)
				page = productRepository.findByCategory(category, pageable);
			else
				page = productRepository.findByCategoryAndNameIgnoreCaseContaining(category, name, pageable);

		}

		httpServletResponse.addIntHeader("totalPages", page.getTotalPages());

		return page.hasContent() ? page.getContent() : new ArrayList<Product>();

	}

	@Override
	public Product findById(int id) {
		Optional<Product> result = productRepository.findById(id);
		Product product = null;
		if (result.isPresent())
			product = result.get();
		else
			throw new RuntimeException("Did not find product id - " + id);
		return product;
	}

	@Override
	public Product save(Product product) {
		product.setId(0); // force creating a new entity
		String base64Image = product.getBase64Image();
		if (base64Image != null) {

			// generate unique file name
			String uniqueImageName = FileUtil.getUniqueFileName() + ".jpg";
			product.setImageName(uniqueImageName);

			// save image to file system
			String path = createImagePath(uniqueImageName);
			boolean saved = FileUtil.saveImageToFileSystem(path, base64Image);
			if (!saved) {
				throw new RuntimeException("failed to save the image to file system");
			}
		} else {
			product.setImageName(Constants.NOT_FOUND_IMAGE_NAME);
		}
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {

		Product tempProduct = productRepository.findById(product.getId()).get();

		if (product.getBase64Image() == null) {
			// keep the old image
			product.setImageName(tempProduct.getImageName());
		} else {

			// save the new image to the file system and update image name in data base
			String uniqueImageName = FileUtil.getUniqueFileName() + ".jpg";
			product.setImageName(uniqueImageName);

			String path = createImagePath(uniqueImageName);
			boolean saved = FileUtil.saveImageToFileSystem(path, product.getBase64Image());
			if (!saved) {
				throw new RuntimeException("failed to save the image to file system");
			}

			// delete old image from file system except no-image.png
			if(!tempProduct.getImageName().equals(Constants.NOT_FOUND_IMAGE_NAME)) {
				String oldImagePath = createImagePath(tempProduct.getImageName());
				boolean deleted = FileUtil.deleteImageFromFileSystem(oldImagePath);
				if (!deleted) {
					throw new RuntimeException("failed to delete image from file system");
				}
			}
		}

		return productRepository.save(product);
	}

	@Override
	public void deleteById(int id) {
		productRepository.deleteById(id);
	}
	
	public String createImagePath(String imageName) {
		return cmsBackEndApplication.getProjectFilesLocation() 
			+ File.separator 
			+ "products_images" 
			+ File.separator 
			+ imageName;
	}

}
