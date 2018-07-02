package com.test.productapi.exception;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1518137420013058026L;
	
	public ResourceNotFoundException(String message) {
        super(message);
    }
}