package com.test.productapi;

import java.util.ArrayList;

import com.test.productapi.vo.ImageVO;
import com.test.productapi.vo.ProductVO;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
public class TestUtils {
	
	/**
	 * 
	 * @param addParentProduct
	 * @return
	 */
	public static ProductVO getMockedProductVO(boolean addParentProduct) {
		
		ProductVO productVO = new ProductVO();
		productVO.setDescription("sample product");
		ImageVO imageVO = new ImageVO();
		imageVO.setType("JPG");
		ArrayList<ImageVO> imageList = new ArrayList<ImageVO>();
		imageList.add(imageVO);
		productVO.setImageList(imageList);
		
		if(addParentProduct) {
			ProductVO parentProductVO = new ProductVO();
			parentProductVO.setDescription("sample parent product");
			ImageVO parentImageVO = new ImageVO();
			parentImageVO.setType("PNG");
			ArrayList<ImageVO> parentImageList = new ArrayList<ImageVO>();
			imageList.add(parentImageVO);
			parentProductVO.setImageList(imageList);
			productVO.setParentProduct(parentProductVO);
		}
		
		return productVO;
	}

}
