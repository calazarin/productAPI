package com.test.productapi.util;

import java.util.Set;

import com.test.productapi.entity.Image;
import com.test.productapi.entity.Product;
import com.test.productapi.vo.ImageVO;
import com.test.productapi.vo.ProductVO;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
public class ProductUtil {
	
	public static Product convertToEntity(ProductVO productVO, boolean isCreate) {
		
		Product product = new Product();
		if(!isCreate) {
			product.setId(productVO.getId());
		}
		product.setDescription(productVO.getDescription());
		return product;
	}
	
	public static ProductVO convertEntityToProductVO(Product product, boolean includeRelantionships) {
		
		ProductVO productVO = createProductVOAndPopulateIt(product);
		
		if(includeRelantionships) {
			
			populateImageVOList(product.getImages(),productVO);
			
			if(product.getParentProduct() != null) {
				ProductVO parentProductVO = createProductVOAndPopulateIt(product.getParentProduct());
				populateImageVOList(product.getParentProduct().getImages(),parentProductVO);
				productVO.setParentProduct(parentProductVO);
			}
			
		}else {
			productVO.setImageList(null);
			productVO.setParentProduct(null);
		}
		return productVO;
	}
	
	private static ProductVO createProductVOAndPopulateIt(Product product) {
		ProductVO productVO = new ProductVO();
		productVO.setId(product.getId());
		productVO.setDescription(product.getDescription());
		return productVO;
	}
	
	private static void populateImageVOList(Set<Image> imageSet, ProductVO productVO) {
		if(imageSet != null && !imageSet.isEmpty()) {
			imageSet.forEach(image ->{
				ImageVO imageVO = new ImageVO();
				imageVO.setId(image.getId());
				imageVO.setType(image.getType());
				imageVO.setProductId(productVO.getId());
				productVO.getImageList().add(imageVO);
			});
		}
	}
}