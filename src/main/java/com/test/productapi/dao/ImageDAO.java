package com.test.productapi.dao;

import java.util.List;

import com.test.productapi.entity.Image;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
public interface ImageDAO extends BaseDAO<Image> {
	
	List<Image> findImagesByProductId(Long productId);

} 