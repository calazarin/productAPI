package com.test.productapi.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
@Getter
@Setter
@ToString
public class ProductVO implements Serializable {
	
	private static final long serialVersionUID = -687991492884005033L;

	@JsonIgnore
	private Long id;

	@NotNull(message="Producto description is mandatory")
	private String description;
	
	private List<ImageVO> imageList = new ArrayList<ImageVO>();
	
	private ProductVO parentProduct;
	
}