/**
 * 
 */
package com.omerio.service.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.MessageFormat;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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


/**
 * @author omerio
 *
 */
@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {

    @InjectMocks PurchaseServiceImpl service;

    @Mock TransactionDao transactionDao; // <-- use annotation instead of Mockito.mock(TransactionDaoImpl.class);

    @Mock CustomerDao customerDao;

    @Mock SellerDao sellerDao;

    @Mock EmailService emailService;

    /**
     * Test method for {@link com.omerio.service.PurchaseServiceImpl#buy(java.math.BigDecimal, java.lang.Long, java.lang.Long)}.
     * @throws MessagingException 
     */
    @Test
    public void testBuy() throws MessagingException {

        /**********
         * Arrange
         **********/
        BigDecimal deliveryFee = new BigDecimal(2.00);

        Customer customer = TestUtils.getCustomer(1L);
        Seller seller = TestUtils.getSeller(1L, deliveryFee);

        // stubbing
        when(customerDao.findById(anyLong())).thenReturn(customer);
        when(sellerDao.findById(anyLong())).thenReturn(seller);

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

        // Notice: we didn't put the emailService.sendEmail in the stubbing above, 
        // rather in the verification block. Below captures the argument being passed to the 
        // sendEmail method
        ArgumentCaptor<EmailMessage> argument = ArgumentCaptor.forClass(EmailMessage.class);
        // use this if you want to verify methods are called the order listed inside the verification block
        // create inOrder object passing any mocks that need to be verified in order
        // InOrder inOrder = inOrder(emailService);
        // inOrder.verify(emailService, times(1)).sendEmail(argument.capture());
        verify(emailService, times(1)).sendEmail(argument.capture());

        assertEquals(argument.getValue().getContent(), expectedMsg);
        assertEquals(argument.getValue().getToEmail(), seller.getEmailAddress());

    }

}
