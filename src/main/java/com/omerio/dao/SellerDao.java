/**
 * 
 */
package com.omerio.dao;

import com.omerio.model.Seller;

/**
 * @author omerio
 *
 */
public interface SellerDao {
	
	/**
	 * Find a particular Seller by id
	 * @param sellerId
	 * @return
	 */
	Seller findById(Long sellerId);

}
