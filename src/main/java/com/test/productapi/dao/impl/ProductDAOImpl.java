package com.test.productapi.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.test.productapi.dao.ProductDAO;
import com.test.productapi.entity.Product;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
@Repository
@Transactional
public class ProductDAOImpl implements ProductDAO{
	
	@PersistenceContext	
	private EntityManager entityManager;	

	@Override
	public void insertOrUpdate(Product product) {
		this.entityManager.persist(product);
	}

	@Override
	public void delete(Product product) {
		this.entityManager.remove(product);
	}

	@Override
	public Product findById(Long id) {
		Query query =  this.entityManager.createQuery("from Product where id = :id");
		query.setParameter("id", id);
		return (Product) query.getSingleResult();
	}

	@Override
	public List<Product> findAll() {
		Query query = this.entityManager.createQuery("from Product");
		return query.getResultList();
	}

	@Override
	public void deleteAll() {
		Query deleteAllImageQuery = this.entityManager.createNativeQuery("Delete from Image");
		deleteAllImageQuery.executeUpdate();
		Query deleteAllQuery = this.entityManager.createNativeQuery("Delete from Product");
		deleteAllQuery.executeUpdate();
	}
}