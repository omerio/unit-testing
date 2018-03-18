/**
 * 
 */
package com.omerio.service.mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import javax.mail.Transport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.omerio.dao.CustomerDao;
import com.omerio.dao.SellerDao;
import com.omerio.dao.TransactionDao;
import com.omerio.model.Customer;
import com.omerio.model.Seller;
import com.omerio.service.PurchaseServiceImpl;
import com.omerio.test.TestUtils;


/**
 * @author omerio
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {
	
	@InjectMocks PurchaseServiceImpl service;
	
	@Mock TransactionDao transactionDao;
	
	@Mock CustomerDao customerDao;
	
	@Mock SellerDao sellerDao;
	
	@Mock Transport transport;

	/**
	 * Test method for {@link com.omerio.service.PurchaseServiceImpl#buy(java.math.BigDecimal, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public void testBuy() {
		
		BigDecimal deliveryFee = new BigDecimal(2.00);
		
		Customer customer = TestUtils.getCustomer(1L);
		Seller seller = TestUtils.getSeller(1L, deliveryFee);
		
		when(customerDao.findById(anyLong())).thenReturn(customer);
		when(sellerDao.findById(anyLong())).thenReturn(seller);
		
		BigDecimal amount = BigDecimal.TEN;
		service.buy(amount, 1L, 1L);
	}

}
