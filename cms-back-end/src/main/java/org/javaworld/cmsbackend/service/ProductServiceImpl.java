package org.javaworld.cmsbackend.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.javaworld.cmsbackend.CmsBackEndApplication;
import org.javaworld.cmsbackend.constants.Constants;
import org.javaworld.cmsbackend.dao.ProductRepository;
import org.javaworld.cmsbackend.entity.Category;
import org.javaworld.cmsbackend.entity.Product;
import org.javaworld.cmsbackend.model.Response;
import org.javaworld.cmsbackend.util.FileUtil;
import org.javaworld.cmsbackend.validator.OnCreate;
import org.javaworld.cmsbackend.validator.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
		
		Pageable pageable = null;
		
		if(pageSize != -1) {
			pageable = PageRequest.of(pageNumber, pageSize);
		}
		
		Category category = new Category(categoryId);
		name = name.trim();
		Page<Product> page = null;

		if (categoryId == 0) {
			page = productRepository.findByNameIgnoreCaseContaining(name, pageable);
		} else {
			page = productRepository.findByCategoryAndNameIgnoreCaseContaining(category, name, pageable);
		}

		httpServletResponse.addIntHeader("totalPages", page.getTotalPages());		
		return page.hasContent() ? page.getContent() : new ArrayList<Product>();
	}

	@Override
	public Product getProductById(int id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		return optionalProduct.orElseThrow(() -> {
			return new RuntimeException("product with id " + id + " not found");
		});
	}

	@Override
	@Transactional
	public Product saveProduct(Product product) {
		
		// force creating a new entity
		product.setId(0); 
		
		product.setVersion(0); //force initial value of version for optimistic locking

		//set product image name
		String base64Image = product.getBase64Image();
		String productImageName = (base64Image == null) ? 
			Constants.NOT_FOUND_IMAGE_NAME : FileUtil.getUniqueFileName() + ".jpg";		
		product.setImageName(productImageName);

		//save product to the data base
		Product savedProduct = productRepository.save(product);
		
		// save product image to file system
		if (product.getBase64Image() != null) {
			String path = createImagePath(savedProduct.getImageName());
			FileUtil.saveImageToFileSystem(path, base64Image);
		}

		return savedProduct;
	}

	@Override
	@Transactional
	public Product updateProduct(Product product) {
		
		//get old product details
		Optional<Product> optionalProduct = productRepository.findById(product.getId());
		String oldImageName = optionalProduct.orElseThrow(() -> {
			return new RuntimeException("product with id " + product.getId() + " not found");
		}).getImageName();
		
		//set product image name  >> keep old image in case base64Image is null
		String productImageName = (product.getBase64Image() == null) ? 
				oldImageName : FileUtil.getUniqueFileName() + ".jpg";		
		product.setImageName(productImageName);
		
		//save product details to the data base
		Product updatedProduct = productRepository.save(product);
		
		//save product image to file system and delete old image
		if(product.getBase64Image() != null) {
			String newImagePath = createImagePath(updatedProduct.getImageName());
			FileUtil.saveImageToFileSystem(newImagePath, product.getBase64Image());
			
			if(!oldImageName.equals(Constants.NOT_FOUND_IMAGE_NAME)) {
				FileUtil.deleteImageFromFileSystem(createImagePath(oldImageName));
			}
		}
		
		return updatedProduct;
	}

	@Override
	@Transactional
	public Response deleteProductById(int productId) {

		//get product details
		Optional<Product> optionalProduct = productRepository.findById(productId);
		String productImageName = optionalProduct.orElseThrow(() -> {
			return new RuntimeException("product with id " + productId + " not found");
		}).getImageName();
		
		//delete product from data base
		productRepository.deleteById(productId);
		
		//delete product image from file system
		if (!productImageName.equals(Constants.NOT_FOUND_IMAGE_NAME)) {
			FileUtil.deleteImageFromFileSystem(createImagePath(productImageName));
		}

		return new Response(true, "product deleted successfully");
	}

	@Override
	@Transactional
	public Response deleteProductImage(String imageName) {
		
		//delete image from data base
		int rowsAffected = productRepository.deleteProductImage(imageName);
		if (rowsAffected == 0) {
			return new Response(false, "no image found with name " + imageName);
		}

		// delete the image from the file system
		FileUtil.deleteImageFromFileSystem(createImagePath(imageName));
		
		return new Response(true, "image deleted successfully");
	}

	private String createImagePath(String imageName) {
		return cmsBackEndApplication.getProjectFilesLocation()
				+ File.separator + "products/products_images" 
				+ File.separator + imageName;
	}

}
