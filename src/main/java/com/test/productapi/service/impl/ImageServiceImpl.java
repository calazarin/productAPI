package com.test.productapi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.productapi.dao.ImageDAO;
import com.test.productapi.entity.Image;
import com.test.productapi.entity.Product;
import com.test.productapi.exception.ServiceException;
import com.test.productapi.service.ImageService;
import com.test.productapi.service.ProductService;
import com.test.productapi.util.ImageUtil;
import com.test.productapi.vo.ImageVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
@Slf4j
@Transactional
@Service
public class ImageServiceImpl implements ImageService {

	private ImageDAO imageDAO;
	
	private ProductService productService;
	
	@Autowired
	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@Override
	public Image createImage(ImageVO imageVO) throws ServiceException {
		log.debug("Creating new image "+imageVO);
		
		Product product = null;
		if(imageVO.getProductId() != null) {
			try {
				product = productService.findProductById(imageVO.getProductId());
			}catch(ServiceException se) {
				log.info("Product not found - creating new image without it - productId - "+imageVO.getProductId());
			}
		}
		Image entity = ImageUtil.convertToEntity(imageVO,true, product);
		this.imageDAO.insertOrUpdate(entity);
		return entity;
	}

	@Override
	public void deleteImage(Long imageId) {
		if(null == imageId) {
			final String errorMsg = "Trying to delete an image with null id";
			log.error(errorMsg);
			new ServiceException(errorMsg);
		}
		log.debug("Deleting image with id "+imageId);
		Image image = imageDAO.findById(imageId);
		imageDAO.delete(image);
	}

	@Override
	public void updateImage(ImageVO imageVO) {
		log.debug("Updating image "+imageVO);
		Image image = imageDAO.findById(imageVO.getId());
		image.setType(imageVO.getType());
		imageDAO.insertOrUpdate(image);
		
	}
	
	@Override
	public List<Image> findAllImages(){
		return this.imageDAO.findAll();
	}
	
	@Override
	public List<ImageVO> findAllImagesAndReturnVOS(){
		List<Image>  imageList = this.imageDAO.findAll();
		List<ImageVO> returningList = new ArrayList<ImageVO>();
		imageList.forEach(image->{
			ImageVO imageVO = ImageUtil.convertEntityToVO(image);
			returningList.add(imageVO);
		});
		return returningList;
	}
	
	@Override
	public void deleteAllImages() {
		log.debug("Deleting all images.");
		this.imageDAO.deleteAll();
	}
	
	@Override
	public Image findImageById(Long imageId) throws ServiceException {
		log.debug("Retrieving image by id: "+imageId);
		Image image = null;
		try {
			image = this.imageDAO.findById(imageId);
		}catch(NoResultException nre) {
			final String errorMessage = "No result found trying to find image with id "+imageId;
			log.error(errorMessage);
			throw new ServiceException(errorMessage);
		}catch(Exception ex) {
			final String errorMessage = "An error happened trying to find image with id "+imageId;
			log.error(errorMessage);
			throw new ServiceException(errorMessage);
		}
		return image;
	}

	@Override
	public List<ImageVO> findImagesForProductId(Long productId) throws ServiceException {
		
		if(productId == null) {
			final String errorMsg = "No possible to find images for product - id is null";
			throw new ServiceException(errorMsg);
		}
		
		List<Image> imageList = this.imageDAO.findImagesByProductId(productId);
		
		List<ImageVO> returningList = new ArrayList<ImageVO>();
		
		imageList.forEach(image->{
			ImageVO imageVO = ImageUtil.convertEntityToVO(image);
			returningList.add(imageVO);
		});
		
		return returningList;
	}

}
