package com.test.productapi.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.test.productapi.dao.ImageDAO;
import com.test.productapi.dao.ProductDAO;
import com.test.productapi.entity.Image;
import com.test.productapi.entity.Product;
import com.test.productapi.exception.ServiceException;
import com.test.productapi.service.ProductService;
import com.test.productapi.util.ImageUtil;
import com.test.productapi.util.ProductUtil;
import com.test.productapi.vo.ImageVO;
import com.test.productapi.vo.ProductVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
@Slf4j
@Transactional
@Service
public class ProductServiceImpl implements ProductService {

	private ProductDAO productDAO;
	
	@Autowired
	private ImageDAO imageDAO;
	
	@Autowired
	public ProductServiceImpl(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	@Override
	public Product createNewProduct(ProductVO productVO) throws ServiceException {
		log.debug("Creating new product "+productVO);
		//new parent + new product?
		Product parentProduct = null;

		if(productVO.getParentProduct() != null) {
			
			//first check if parent already exists
			if(productVO.getParentProduct().getId() != null) {
				try {
					parentProduct = this.findProductById(productVO.getParentProduct().getId());
				}catch(ServiceException ex) {
					log.info("Parent product not found - creating a new one");
				}
			}
			//parent product not found; let's create a new one
			if(parentProduct == null) {
				//persist parent
				parentProduct = persistProduct(productVO.getParentProduct(), null, true);
				
				//persist child
				return persistProduct(productVO, parentProduct, true);
				
			}else {
				//we found given parent - let's set it to new product we wish create
				return persistProduct(productVO, parentProduct, true);
			}
		}else {
			//creating orphan product
			return persistProduct(productVO, null,true);
		}
	}
	
	private Product persistProduct(ProductVO productVO, Product parentProduct, boolean isCreating) {
		Product product = ProductUtil.convertToEntity(productVO, isCreating);
		if(parentProduct != null) {
			product.setParentProduct(parentProduct);
		}
		productDAO.insertOrUpdate(product);
		if(!CollectionUtils.isEmpty(productVO.getImageList())) {
			this.saveImageVOList(productVO.getImageList(), product, true);
		}
		return product;
	}
	
	private void saveImageVOList(List<ImageVO> imageVOList, Product product, boolean isCreate) {
		
		imageVOList.forEach(imageVO -> {
			Image image = ImageUtil.convertToEntity(imageVO,isCreate);
			image.setProduct(product);
			product.getImages().add(image);
			imageDAO.insertOrUpdate(image);
		});
		
	}
	
	public Product findProductById(Long productId) throws ServiceException{
		Product product = null;
		try {
			product = this.productDAO.findById(productId);
		}catch(NoResultException nre) {
			final String errorMessage = "No result found trying to find product with id "+productId;
			log.error(errorMessage);
			throw new ServiceException(errorMessage);
		}catch(Exception ex) {
			final String errorMessage = "An error happened trying to find product with id "+productId;
			log.error(errorMessage);
			throw new ServiceException(errorMessage);
		}
		return product;
	}
	
	public ProductVO findProductById(Long productId, boolean includeRelationship) throws ServiceException{
		log.debug("Retrieving product by id: "+productId);
		Product product = null;
		try {
			product = this.productDAO.findById(productId);
		}catch(NoResultException nre) {
			final String errorMessage = "No result found trying to find product with id "+productId;
			log.error(errorMessage);
			throw new ServiceException(errorMessage);
		}catch(Exception ex) {
			final String errorMessage = "An error happened trying to find product with id "+productId;
			log.error(errorMessage);
			throw new ServiceException(errorMessage);
		}
		return ProductUtil.convertEntityToProductVO(product,includeRelationship);
	}
	
	@Override
	public void deleteProduct(Long productId){
		if(null == productId) {
			final String errorMsg = "Trying to delete a product with null id";
			log.error(errorMsg);
			new ServiceException(errorMsg);
		}
		
		Product product = productDAO.findById(productId);
		log.debug("Deleting product with id "+productId);
		
		this.productDAO.delete(product);
		log.debug("Product with id "+productId+"successfully deleted.");
	}

	@Override
	public Product updateProduct(ProductVO productVO) {
		if(null == productVO) {
			final String errorMsg = "Not possible to update pruduct - productVO is null";
			log.error(errorMsg);
			new ServiceException(errorMsg);
		}
		
		log.debug("Update product "+productVO);
		Product product = productDAO.findById(productVO.getId());
		
		product.setDescription(productVO.getDescription());
		if(productVO.getParentProduct() != null) {
			product.setParentProduct(ProductUtil.convertToEntity(productVO.getParentProduct(), false));
		}
	
		this.productDAO.insertOrUpdate(product);
		
		log.debug("Product "+productVO.getId()+"successfully updated");
		
		return product;
	}
	
	@Override
	public List<ProductVO> findAllProducts(boolean includeRelationship){
		log.debug("Searching for all products");
		List<Product> productList = this.productDAO.findAll();
		log.debug("Found "+productList.size()+" products.");
		List<ProductVO> productVOList = new ArrayList<ProductVO>();
		
		productList.forEach(product -> {
			productVOList.add(ProductUtil.convertEntityToProductVO(product, includeRelationship));
		});
		
		return productVOList;
	}
	
	@Override
	public void deleteAllProducts() {
		log.debug("Deleting all products.");
		this.productDAO.deleteAll();
	}

	@Override
	public List<ProductVO> findChildrenForParentId(Long parentId) throws ServiceException {
		log.debug("Searching for all children - parent id "+parentId);

		Product parentProduct = this.findProductById(parentId);
		
		List<ProductVO> childrenList = new ArrayList<ProductVO>();
		
		if(parentProduct.getProducts() != null && !parentProduct.getProducts().isEmpty()) {
			parentProduct.getProducts().forEach(child -> {
				childrenList.add(ProductUtil.convertEntityToProductVO(child, false));
			});
		}
		
		return childrenList;
	}
}