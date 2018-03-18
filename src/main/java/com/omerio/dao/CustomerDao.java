/**
 * 
 */
package com.omerio.dao;

import com.omerio.model.Customer;

/**
 * @author omerio
 *
 */
public interface CustomerDao {
	

	Customer findById(Long customerId);

}
