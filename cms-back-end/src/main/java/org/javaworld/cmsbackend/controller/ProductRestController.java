package org.javaworld.cmsbackend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.javaworld.cmsbackend.CmsBackEndApplication;
import org.javaworld.cmsbackend.constants.Constants;
import org.javaworld.cmsbackend.entity.Product;
import org.javaworld.cmsbackend.model.Response;
import org.javaworld.cmsbackend.service.ProductService;
import org.javaworld.cmsbackend.validator.OnCreate;
import org.javaworld.cmsbackend.validator.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api")
public class ProductRestController {

	@Autowired
	private ProductService productService;

	@Autowired
	private HttpServletResponse httpServletResponse;
	
	@Autowired
	CmsBackEndApplication cmsBackEndApplication;
	

	@GetMapping("/products/getImage/{imageName}")
	public void geProductImage(@PathVariable String imageName) throws IOException {
		String path = cmsBackEndApplication.getProjectFilesLocation() + File.separator + "products_images"
				+ File.separator + imageName;
		InputStream in = new FileInputStream(path);
		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		IOUtils.copy(in, httpServletResponse.getOutputStream());
		in.close();
	}

	@GetMapping("/products/all")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/products")
	public List<Product> getProducts(@RequestParam String searchTerm, 
									 @RequestParam int categoryId,
									 @RequestParam int pageNumber, 
									 @RequestParam int pageSize) {

		return productService.getProducts(searchTerm, categoryId, pageNumber, pageSize);
	}

	@GetMapping("/products/{productId}")
	public Product getProduct(@PathVariable int productId) {
		return productService.findById(productId);
	}

	@PostMapping("/products")
	public Product addProduct(@Validated(value = { OnCreate.class }) 
	                          @RequestBody Product product) {
		return productService.save(product);
	}

	@PutMapping("/products")
	public Product updateProduct(@Validated(value = { OnUpdate.class }) 
	                             @RequestBody Product product) {
		return productService.update(product);
	}

	@DeleteMapping("/products/{productId}")
	public Response deleteProduct(@PathVariable int productId) {
		Product tempProduct = productService.findById(productId);

		if (tempProduct == null) {
			return new Response(Constants.NOT_FOUND_STATUS, "Product id not found - " + productId);
			// throw new RuntimeException("Product id not found - " + productId);
		}

		productService.deleteById(productId);
		return new Response(Constants.OK_STATUS, "Deleted product id - " + productId);
	}

}
