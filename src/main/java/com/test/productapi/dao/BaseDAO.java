package com.test.productapi.dao;

import java.util.List;

/**
 * @author Lazarin, Carlos
 *
 * @param <T>
 */
public interface BaseDAO<T> {

	void insertOrUpdate(T t);
	
	void delete(T t);
	
	T findById(Long id);
	
	List<T> findAll();
	
	void deleteAll();
	
}