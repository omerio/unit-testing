/**
 * 
 */
package com.omerio.service;

import java.math.BigDecimal;

import com.omerio.model.Transaction;

/**
 * @author omerio
 *
 */
public interface PurchaseService {
	
	BigDecimal SELLER_FEES = new BigDecimal("0.0156");
	
	BigDecimal TAX = new BigDecimal("0.05");
	
	String EMAIL_TEMPLATE = "You have received ${0} from {1}.";
	
	/**
	 * Make a purchase from the seller
	 * @param amount
	 * @param customerId
	 * @param sellerId
	 * @return
	 */
	Transaction buy(BigDecimal amount, Long customerId, Long sellerId);

}
