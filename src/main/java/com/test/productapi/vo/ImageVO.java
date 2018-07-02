package com.test.productapi.vo;

import java.io.Serializable;

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
public class ImageVO implements Serializable{

	private static final long serialVersionUID = 4789859045437504662L;

	@JsonIgnore
	private Long id;

	@NotNull(message="Image type is required")
	private String type;
	
	private Long productId;
}