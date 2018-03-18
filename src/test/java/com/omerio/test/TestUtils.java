/**
 * 
 */
package com.omerio.test;

import java.math.BigDecimal;

import com.omerio.model.Customer;
import com.omerio.model.Seller;

/**
 * @author omerio
 *
 */
public class TestUtils {
	
	public static Customer getCustomer(Long id) {
		Customer customer = new Customer();
		customer.setEmailAddress("john.smith@gmail.com");
		customer.setName("John Smith");
		customer.setId(id);
		return customer;
	}

	public static Seller getSeller(Long id, BigDecimal deliveryFee) {
		Seller seller = new Seller();
		seller.setDeliveryFees(deliveryFee);
		seller.setEmailAddress("seller@example.com");
		seller.setName("Awesome Seller");
		seller.setId(id);
		return seller;
	}
}
