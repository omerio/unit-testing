/**
 * 
 */
package com.omerio.test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.omerio.model.Customer;
import com.omerio.model.Seller;
import com.omerio.model.Transaction;
import com.omerio.service.PurchaseService;

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

    public static Transaction getTxn(Long customerId, Long sellerId, BigDecimal amount, BigDecimal deliveryFee) {
        // transaction
        Transaction txn = new Transaction();

        txn.setCustomer(getCustomer(customerId));
        txn.setSeller(getSeller(sellerId, deliveryFee));

        txn.setAmount(amount);
        txn.setDeliveryCharge(deliveryFee);
        txn.setDate(new Date());
        txn.setReference(UUID.randomUUID().toString());

        // populate the fees
        BigDecimal serviceFees = amount.multiply(PurchaseService.SELLER_FEES, new MathContext(2, RoundingMode.HALF_UP));
        txn.setServiceFees(serviceFees);

        // populate the tax
        BigDecimal tax = amount.multiply(PurchaseService.TAX, new MathContext(2, RoundingMode.HALF_UP));
        txn.setTax(tax);
        return txn;
    }

    public static Message createMimeMessage() {
        Session session = Session.getInstance(new Properties()); 

        Message message = new MimeMessage(session);
        return message;
    }

}
