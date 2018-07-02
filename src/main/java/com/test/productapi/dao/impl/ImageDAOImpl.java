package com.test.productapi.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.test.productapi.dao.ImageDAO;
import com.test.productapi.entity.Image;

/**
 * 
 * @author Lazarin, Carlos
 *
 */
@Repository
@Transactional
public class ImageDAOImpl implements ImageDAO {

	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Override
	public void insertOrUpdate(Image image) {
		this.entityManager.persist(image);
	}

	@Override
	public void delete(Image image) {
		this.entityManager.remove(image);
	}

	@Override
	public Image findById(Long id) {
		Query query =  this.entityManager.createQuery("from Image where id = :id");
		query.setParameter("id", id);
		return (Image) query.getSingleResult();
	}

	@Override
	public List<Image> findAll() {
		Query query = this.entityManager.createQuery("from Image");
		return query.getResultList();
	}

	@Override
	public void deleteAll() {
		Query deleteAllQuery = this.entityManager.createNativeQuery("Delete from Image");
		deleteAllQuery.executeUpdate();
	}

	@Override
	public List<Image> findImagesByProductId(Long productId) {
		Query query = this.entityManager.createQuery("from Image where product.id = :productId");
		query.setParameter("productId", productId);
		return query.getResultList();
	}

}