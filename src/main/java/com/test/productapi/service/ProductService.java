package com.test.productapi.service;

import java.util.List;

import com.test.productapi.entity.Product;
import com.test.productapi.exception.ServiceException;
import com.test.productapi.vo.ProductVO;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
public interface ProductService {
	
	Product createNewProduct(ProductVO productVO) throws ServiceException;
	
	void deleteProduct(Long productId);
	
	Product updateProduct(ProductVO productVO);
	
	ProductVO findProductById(Long productId, boolean includeRelationship) throws ServiceException;
	
	List<ProductVO> findAllProducts(boolean includeRelationship);
	
	List<ProductVO> findChildrenForParentId(Long parentId) throws ServiceException;
	
	void deleteAllProducts();
	
	Product findProductById(Long productId) throws ServiceException;
}