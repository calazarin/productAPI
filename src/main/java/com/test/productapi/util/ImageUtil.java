package com.test.productapi.util;

import com.test.productapi.entity.Image;
import com.test.productapi.entity.Product;
import com.test.productapi.vo.ImageVO;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
public class ImageUtil {

	public static Image convertToEntity(ImageVO imageVO, boolean isCreate) {
		Image image = new Image();
		if(!isCreate) {
			image.setId(imageVO.getId());
		}
		image.setType(imageVO.getType());
		return image;
	}
	
	public static Image convertToEntity(ImageVO imageVO, boolean isCreate, Product product) {
		Image image = new Image();
		if(!isCreate) {
			image.setId(imageVO.getId());
		}
		if(product != null) {
			image.setProduct(product);
		}
		image.setType(imageVO.getType());
		return image;
	}
	
	public static ImageVO convertEntityToVO(Image image) {
		ImageVO imageVO = new ImageVO();
		imageVO.setId(image.getId());
		imageVO.setType(image.getType());
		if(image.getProduct() != null) {
			imageVO.setProductId(image.getProduct().getId());
		}
		return imageVO;
	}
	
}
