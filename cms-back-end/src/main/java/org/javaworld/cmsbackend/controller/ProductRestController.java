package org.javaworld.cmsbackend.controller;

import java.io.IOException;
import java.util.List;
import org.javaworld.cmsbackend.CmsBackEndApplication;
import org.javaworld.cmsbackend.entity.Product;
import org.javaworld.cmsbackend.model.Response;
import org.javaworld.cmsbackend.service.AttachmentService;
import org.javaworld.cmsbackend.service.ProductService;
import org.javaworld.cmsbackend.validator.OnCreate;
import org.javaworld.cmsbackend.validator.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
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
	CmsBackEndApplication cmsBackEndApplication;
	
	@Autowired
	private AttachmentService attachmentService;

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
		return productService.deleteById(productId);
	}

	@GetMapping("/products/products_images/{imageName}")
	public void getProductImage() throws IOException {
		attachmentService.getAttachment();
	}

	@DeleteMapping("/products/deleteImage/{imageName}")
	public Response deleteProductImage(@PathVariable String imageName) {
		return productService.deleteProductImage(imageName);
	}

}
