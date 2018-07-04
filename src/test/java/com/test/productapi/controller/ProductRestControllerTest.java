package com.test.productapi.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.test.productapi.service.ProductService;
import com.test.productapi.vo.ProductVO;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductRestController.class)
public class ProductRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private ProductService productService;
	
	@Mock
	private ServletUriComponentsBuilder servletUriComponentsBuilder;
	
	@Mock
	private UriComponentsBuilder uriComponentsBuilder;
	
	@Mock
	private UriComponents uriComponents;
	
	private String jsonPayload() {
		ProductVO stub = new ProductVO();
		stub.setDescription("sample description");
		Gson gson = new Gson();
	    return gson.toJson(stub);
	}

	@Test
	public void testUpdateProduct() throws Exception {
		this.mockMvc.perform(
				put("/products")
				.content(this.jsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andDo(print())
		.andExpect(status()
				.isOk())
		.andExpect(content()
				.string(containsString("Product updated successfully")));
	}
	
	@Test
	public void testDeleteProduct() throws Exception {
		this.mockMvc.perform(
				delete("/products/1")
				)
		.andDo(print())
		.andExpect(status()
				.isNoContent())
		.andExpect(content()
				.string(containsString("Product deleted successfully")));
	}
	
	@Test
	public void testListAllProducts() throws Exception {
		this.mockMvc.perform(
				get("/products/false")
				)
		.andDo(print())
		.andExpect(status()
				.isOk());
	}
	
}
