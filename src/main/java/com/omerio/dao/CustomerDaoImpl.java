/**
 * 
 */
package com.omerio.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.omerio.model.Customer;

/**
 * @author omerio
 *
 */
@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Customer findById(Long customerId) {
		return entityManager.find(Customer.class, customerId);
	}

}
