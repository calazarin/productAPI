package com.test.productapi.service;

import java.util.List;

import com.test.productapi.entity.Image;
import com.test.productapi.exception.ServiceException;
import com.test.productapi.vo.ImageVO;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
public interface ImageService {

	Image createImage(ImageVO imageVO) throws ServiceException;
	
	void deleteImage(Long imageId);
	
	void updateImage(ImageVO imageVO);
	
	List<Image> findAllImages();
	
	void deleteAllImages();
	
	Image findImageById(Long imageId) throws ServiceException;
	
	List<ImageVO> findImagesForProductId(Long productId) throws ServiceException;
	
	List<ImageVO> findAllImagesAndReturnVOS();
}