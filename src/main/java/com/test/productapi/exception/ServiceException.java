package com.test.productapi.exception;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1578622910836049037L;

	public ServiceException(String errorMsg) {
		super(errorMsg);
	}
}