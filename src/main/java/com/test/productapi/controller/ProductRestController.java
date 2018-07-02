package com.test.productapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.productapi.entity.Product;
import com.test.productapi.exception.ResourceNotFoundException;
import com.test.productapi.exception.ServiceException;
import com.test.productapi.service.ProductService;
import com.test.productapi.vo.ProductVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * Product resource rest controllers
 * 
 * @author Lazarin,Carlos
 * 
 *         POST -> /products PUT -> /products 
 *         DELETE -> /products/{id} 
 *         GET -> /products/{includeRelantionship} 
 *         GET -> /products/details/{includeRelantionship} 
 *         GET -> /products/details/{includeRelantionship}/{parentId} 
 *         GET -> /products/children/{parentId}
 * 
 */
@RestController
public class ProductRestController {

	private ProductService productService;

	private static final String PRODUCT_CREATED_SUCCESSFULLY_MSG = "New product created successfully";
	private static final String PRODUCT_DELETED_SUCCESSFULLY_MSG = "Product deleted successfully";
	private static final String PRODUCT_UPDATED_SUCCESSFULLY_MSG = "Product updated successfully";

	@Autowired
	public ProductRestController(ProductService productService) {
		this.productService = productService;
	}

	@ApiOperation(value = "Creates a new product for given payload", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = PRODUCT_CREATED_SUCCESSFULLY_MSG), })
	@PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createProduct(@Valid @RequestBody ProductVO productVO) throws ServiceException {
		Product createdProduct = productService.createNewProduct(productVO);

		/*
		 * URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		 * .buildAndExpand(createdProduct.getId()).toUri();
		 * 
		 * return ResponseEntity.created(location).build();
		 */
		return new ResponseEntity<String>(PRODUCT_CREATED_SUCCESSFULLY_MSG, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Updates product for given payload", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = PRODUCT_UPDATED_SUCCESSFULLY_MSG), })
	@PutMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductVO productVO) {
		productService.updateProduct(productVO);
		return new ResponseEntity<String>(PRODUCT_UPDATED_SUCCESSFULLY_MSG, HttpStatus.OK);
	}

	@ApiOperation(value = "Deletes product for given id", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = PRODUCT_DELETED_SUCCESSFULLY_MSG), })
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<String>(PRODUCT_DELETED_SUCCESSFULLY_MSG, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "List all products - you might either include relationships (images + parent) or not", responseContainer = "List", response = ProductVO.class)
	@GetMapping(value = "/products/{includeRelationship}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductVO>> getAllProducts(@PathVariable boolean includeRelationship) {
		return new ResponseEntity<List<ProductVO>>(this.productService.findAllProducts(includeRelationship),
				HttpStatus.OK);
	}

	@ApiOperation(value = "List product details - you might either include relationships (images + parent) or not", responseContainer = "List", response = ProductVO.class)
	@GetMapping(value = "/products/details/{includeRelationship}/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductVO> getProductDetails(@PathVariable boolean includeRelationship,
			@PathVariable Long productId) throws ServiceException {
		try {
			return new ResponseEntity<ProductVO>(this.productService.findProductById(productId, includeRelationship),
					HttpStatus.OK);
		} catch (ServiceException sex) {
			throw new ResourceNotFoundException(sex.getMessage());
		}
	}

	@ApiOperation(value = "List of children products for given product parent id", responseContainer = "List", response = ProductVO.class)
	@GetMapping(value = "/products/children/{parentId}")
	public ResponseEntity<List<ProductVO>> getChildrenProducts(@PathVariable Long parentId) throws ServiceException {
		try {
			return new ResponseEntity<List<ProductVO>>(this.productService.findChildrenForParentId(parentId),
					HttpStatus.OK);
		} catch (ServiceException sex) {
			throw new ResourceNotFoundException(sex.getMessage());
		}
	}

}