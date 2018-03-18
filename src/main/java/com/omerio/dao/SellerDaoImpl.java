/**
 * 
 */
package com.omerio.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.omerio.model.Seller;

/**
 * @author omerio
 *
 */
@Repository
@Transactional
public class SellerDaoImpl implements SellerDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Seller findById(Long sellerId) {
		return entityManager.find(Seller.class, sellerId);
	}

}
