package com.test.productapi.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.productapi.Application;
import com.test.productapi.TestUtils;
import com.test.productapi.entity.Image;
import com.test.productapi.exception.ServiceException;
import com.test.productapi.service.ImageService;
import com.test.productapi.service.ProductService;
import com.test.productapi.util.ImageUtil;
import com.test.productapi.vo.ImageVO;
import com.test.productapi.vo.ProductVO;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ImageServiceImplTest {

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ProductService productService;
	
	@After
	public void doAfter() {
		this.imageService.deleteAllImages();
	}
	
	@Test
	public void testCreateNewImage() throws ServiceException {
		ImageVO imageVO = new ImageVO();
		this.imageService.createImage(imageVO);
		List<Image> imageList = this.imageService.findAllImages();
		assertEquals(1, imageList.size());
	}
	
	@Test
	public void testDeleteImage() throws ServiceException {
		ImageVO imageVO = new ImageVO();
		this.imageService.createImage(imageVO);
		List<Image> imageList = this.imageService.findAllImages();
		assertEquals(1, imageList.size());
		this.imageService.deleteImage(imageList.get(0).getId());
		List<Image> imageListAfterDeleing = this.imageService.findAllImages();
		assertEquals(0, imageListAfterDeleing.size());
		
	}
	
	@Test
	public void testUpdateImage() throws ServiceException {
		ImageVO imageVO = new ImageVO();
		imageVO.setType("JPG");
		this.imageService.createImage(imageVO);
		List<Image> imageList = this.imageService.findAllImages();
		Image image = imageList.get(0);
		image.setType("PNG");
		this.imageService.updateImage(ImageUtil.convertEntityToVO(image));
		Image updatedImage = this.imageService.findImageById(image.getId());
		assertEquals("PNG", updatedImage.getType());
	}
	
	@Test 
	public void testFindImagesByProductId() throws ServiceException {
		
		ProductVO productVO = TestUtils.getMockedProductVO(false);
		this.productService.createNewProduct(productVO);
		List<ProductVO> productList = this.productService.findAllProducts(false);
		productService.createNewProduct(productVO);
		
		ImageVO imageVO = new ImageVO();
		imageVO.setType("JPG");
		imageVO.setProductId(productList.get(0).getId());
		
		this.imageService.createImage(imageVO);
		
		List<ImageVO> resp = this.imageService.findImagesForProductId(productList.get(0).getId());
		
		assertNotNull(resp);
		assertEquals("JPG", resp.get(0).getType());
		assertEquals(productList.get(0).getId(), resp.get(0).getProductId());
	}
}
