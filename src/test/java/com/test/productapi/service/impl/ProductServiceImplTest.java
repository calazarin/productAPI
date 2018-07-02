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
import com.test.productapi.entity.Product;
import com.test.productapi.exception.ServiceException;
import com.test.productapi.service.ProductService;
import com.test.productapi.vo.ProductVO;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductServiceImplTest {
	
	@Autowired
	private ProductService productService;

	@Test
	public void testCreateNewProduct_no_parent() throws ServiceException {
		ProductVO productVO = TestUtils.getMockedProductVO(false);
		this.productService.createNewProduct(productVO);
		List<ProductVO> productList = this.productService.findAllProducts(true);
		assertEquals(1,productList.size());
	}
	
	@Test
	public void testCreateNewProduct_with_new_parent() throws ServiceException {
		this.productService.deleteAllProducts();
		ProductVO productVO = TestUtils.getMockedProductVO(true);
		this.productService.createNewProduct(productVO);
		List<ProductVO> productList = this.productService.findAllProducts(true);
		assertEquals(2,productList.size());
	}

	@Test
	public void testFindProductById() throws ServiceException {
		
		this.productService.createNewProduct(TestUtils.getMockedProductVO(false));
		
		List<ProductVO> productList = this.productService.findAllProducts(false);
		
		Product product = this.productService.findProductById(productList.get(0).getId());
		
		assertNotNull(product);
		assertEquals(productList.get(0).getId(),product.getId());
		
	}
	
	@Test
	public void testFindChildrenForParentId() throws ServiceException {
		
		this.productService.createNewProduct(TestUtils.getMockedProductVO(true));
		
		List<ProductVO> productList = this.productService.findAllProducts(true);
		
		ProductVO parentProduct = null;
		
		for(ProductVO prdt : productList) {
			if(prdt.getParentProduct() == null) {
				parentProduct = prdt;
				break;
			}
		}
		
		List<ProductVO> childrens = this.productService.findChildrenForParentId(parentProduct.getId());
		
		assertNotNull(childrens);
		assertEquals(1,childrens.size());
		
	}
	
	@Test
	public void testDeleteProduct_no_parent() throws ServiceException {
		ProductVO productVO = TestUtils.getMockedProductVO(false);
		this.productService.createNewProduct(productVO);
		List<ProductVO> productListBeforeDeleting = this.productService.findAllProducts(true);
		assertEquals(1,productListBeforeDeleting.size());
		this.productService.deleteProduct(productListBeforeDeleting.get(0).getId());
		List<ProductVO> productListAfterDeleting = this.productService.findAllProducts(true);
		assertEquals(0,productListAfterDeleting.size());
	}
	
	@Test
	public void testDeleteProduct_with_parent_but_not_delete_parent() throws ServiceException {
		ProductVO productVO = TestUtils.getMockedProductVO(true);
		this.productService.createNewProduct(productVO);
		List<ProductVO> productListBeforeDeleting = this.productService.findAllProducts(true);
		assertEquals(2,productListBeforeDeleting.size());
		for(ProductVO productVOBeforeDeleting : productListBeforeDeleting) {
			if(productVOBeforeDeleting.getParentProduct() != null) {
				this.productService.deleteProduct(productVOBeforeDeleting.getId());
				break;
			}
		}
		List<ProductVO> productListAfterDeleting = this.productService.findAllProducts(true);
		assertEquals(1,productListAfterDeleting.size());
	}
	
	@After
	public void doAfter() {
		this.productService.deleteAllProducts();
	}
	
	@Test
	public void testUpdateProduct_no_parent() throws ServiceException {
		
		this.productService.createNewProduct(TestUtils.getMockedProductVO(false));
		List<ProductVO> productList = this.productService.findAllProducts(true);
		
		ProductVO productVO = productList.get(0);
		productVO.setDescription("updatedDescription");
		
		this.productService.updateProduct(productVO);
		
		ProductVO updatedProductVO = this.productService.findProductById(productVO.getId(),false);
		assertEquals("updatedDescription",updatedProductVO.getDescription());
	}
	
	@Test
	public void testUpdateProduct_with_parent() throws ServiceException {
		
		this.productService.createNewProduct(TestUtils.getMockedProductVO(true));
		List<ProductVO> productList = this.productService.findAllProducts(true);
		
		ProductVO productVO = productList.get(0);
		productVO.setDescription("updatedDescription");
		
		this.productService.updateProduct(productVO);
		
		ProductVO updatedProductVO = this.productService.findProductById(productVO.getId(),false);
		assertEquals("updatedDescription",updatedProductVO.getDescription());
	}
	
}