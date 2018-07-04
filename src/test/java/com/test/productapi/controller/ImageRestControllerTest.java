package com.test.productapi.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.test.productapi.entity.Image;
import com.test.productapi.entity.Product;
import com.test.productapi.service.ImageService;
import com.test.productapi.vo.ImageVO;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ImageRestController.class)
public class ImageRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private ImageService imageService;

	private String jsonPayload() {
		ImageVO stub = new ImageVO();
		stub.setType("JPG");
		Gson gson = new Gson();
	    return gson.toJson(stub);
	}

	@Test
	public void testUpdateImage() throws Exception {
		this.mockMvc.perform(
				put("/images")
				.content(this.jsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andDo(print())
		.andExpect(status()
				.isOk())
		.andExpect(content()
				.string(containsString("Image updated successfully")));
	}
	
	@Test
	public void testDeleteImage() throws Exception {
		this.mockMvc.perform(
				delete("/images/1")
				)
		.andDo(print())
		.andExpect(status()
				.isNoContent())
		.andExpect(content()
				.string(containsString("Image deleted successfully")));
	}
	
}
