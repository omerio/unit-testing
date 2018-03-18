/**
 * 
 */
package com.omerio.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omerio.dao.CustomerDao;
import com.omerio.dao.SellerDao;
import com.omerio.dao.TransactionDao;
import com.omerio.model.Customer;
import com.omerio.model.Seller;
import com.omerio.model.Transaction;

/**
 * @author omerio
 *
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {
	
	private static Log log = LogFactory.getLog(PurchaseServiceImpl.class);
	
	@Autowired
	private SellerDao sellerDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	private MathContext context = new MathContext(2, RoundingMode.HALF_UP);
	
	public Transaction buy(BigDecimal amount, Long customerId, Long sellerId) {
		
		// validation
		if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Transaction amount must provided and greater than zero");
		}
		
		// load the customer
		Customer customer = null;
		if(customerId != null) {
			customer = customerDao.findById(customerId);
		}
		
		if(customer == null) {
			throw new IllegalArgumentException("Invalid customer id: " + customerId);
		}
		
		// load the seller
		Seller seller = null;
		if(sellerId != null) {
			seller = sellerDao.findById(sellerId);
		}
		
		if(seller == null) {
			throw new IllegalArgumentException("Invalid seller id: " + sellerId);
		}
		
		// transaction
		Transaction txn = new Transaction();
		
		txn.setCustomer(customer);
		txn.setSeller(seller);
		
		txn.setAmount(amount);
		txn.setDeliveryCharge(seller.getDeliveryFees());
		txn.setDate(new Date());
		txn.setReference(UUID.randomUUID().toString());
		
		// populate the fees
		BigDecimal serviceFees = amount.multiply(SELLER_FEES, context);
		txn.setServiceFees(serviceFees);
		
		// populate the tax
		BigDecimal tax = amount.multiply(TAX, context);
		txn.setTax(tax);
		
		// save the transaction
		transactionDao.save(txn);
		
		// send email to seller
		this.sendSellerEmail(txn);
		
		return txn;
	}
	
	
	/**
	 * Realistically you would use Spring Email and save emails to the database with retry mechanism, etc.
	 * but this is to demonstrate mocking/faking of javax.mail.Transport
	 * Send an email to the seller
	 * @param txn
	 */
	private void sendSellerEmail(Transaction txn) {
		
		Customer customer = txn.getCustomer();
		Seller seller = txn.getSeller();
		
		String msg = MessageFormat.format(EMAIL_TEMPLATE, 
				txn.calculateSellerNetAmount(), customer.getName());

		try {
			// create email session, etc.
			Session session = Session.getInstance(new Properties()); 

			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress("noreply@example.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(seller.getEmailAddress()));
			message.setSubject("Payment Received");
			message.setText(msg);

			Transport.send(message);
			
		} catch(Exception e) {
			
			log.error("Sending seller email has failed for seller: " + txn.getSeller().getId(), e);
		}
	}

}
