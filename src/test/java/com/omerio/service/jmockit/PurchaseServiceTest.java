/**
 * 
 */
package com.omerio.service.jmockit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.omerio.dao.CustomerDao;
import com.omerio.dao.SellerDao;
import com.omerio.dao.TransactionDao;
import com.omerio.model.Customer;
import com.omerio.model.EmailMessage;
import com.omerio.model.Seller;
import com.omerio.model.Transaction;
import com.omerio.service.EmailService;
import com.omerio.service.PurchaseService;
import com.omerio.service.PurchaseServiceImpl;
import com.omerio.test.TestUtils;
import com.omerio.test.category.UnitTest;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

/**
 * @author omerio
 *
 */
// either include JMockit dependency before JUnit or use @RunWith
//@RunWith(JMockit.class)
@Category(UnitTest.class)
public class PurchaseServiceTest {

    @Tested PurchaseServiceImpl service;

    @Injectable TransactionDao transactionDao;

    @Injectable CustomerDao customerDao;

    @Injectable SellerDao sellerDao;

    @Injectable EmailService emailService;


    /**
     * Test method for {@link com.omerio.service.PurchaseServiceImpl#buy(java.math.BigDecimal, java.lang.Long, java.lang.Long)}.
     * @throws MessagingException 
     * @throws IOException 
     */
    @Test
    public void testBuy() throws MessagingException, IOException {

        /**********
         * Arrange
         **********/
        BigDecimal deliveryFee = new BigDecimal(2.00);

        final Customer customer = TestUtils.getCustomer(1L);
        final Seller seller = TestUtils.getSeller(1L, deliveryFee);

        new Expectations() {{
            customerDao.findById(anyLong); result = customer; times = 1;
            sellerDao.findById(anyLong); result = seller; times = 1;
        }};

        BigDecimal amount = BigDecimal.TEN;

        /**********
         * Act
         **********/
        Transaction txn = service.buy(amount, 1L, 1L);

        /**********
         * Assert
         **********/
        // 11.34 = amount(10) - fees(0.0156 * 10) - tax(0.05 * 10) + deliveryFee(2.00)
        final BigDecimal sellerTotal = new BigDecimal(11.34);
        final String expectedMsg = MessageFormat.format(
                PurchaseService.EMAIL_TEMPLATE, sellerTotal, "John Smith");

        assertNotNull(txn);
        assertEquals(txn.getAmount(), amount);
        assertEquals(txn.getDeliveryCharge(), seller.getDeliveryFees());
        assertEquals(txn.getTax(), new BigDecimal("0.50"));
        assertEquals(txn.getServiceFees(), new BigDecimal("0.16"));

        // new VerificationsInOrder() {{ <-- use this if you want to verify methods are called
        // the order listed inside the verification block
        new Verifications() {{
            EmailMessage msg;
            // Notice: we didn't put the emailService.sendEmail in the expectation above, 
            // rather in the verification block. Below captures the argument being passed to the 
            // sendEmail method
            emailService.sendEmail(msg = withCapture()); times = 1;

            assertEquals(msg.getContent(), expectedMsg);
            assertEquals(msg.getToEmail(), seller.getEmailAddress());

        }};
    }

}
