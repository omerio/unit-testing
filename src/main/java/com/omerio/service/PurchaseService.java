package com.omerio.service;

import java.math.BigDecimal;

import com.omerio.model.Transaction;

/**
 * @author omerio
 *
 */
public interface PurchaseService {

    // PMD & FindBugs fail if we don't use a string representation
    // http://findbugs.sourceforge.net/bugDescriptions.html#DMI_BIGDECIMAL_CONSTRUCTED_FROM_DOUBLE
    BigDecimal SELLER_FEES = new BigDecimal("0.0156");

    BigDecimal TAX = new BigDecimal("0.05");

    String EMAIL_TEMPLATE = "You have received ${0} from {1}.";

    /**
     * Make a purchase from the seller
     * @param amount - the transaction amount
     * @param customerId - the id of the customer making the purchase
     * @param sellerId - the id of the seller
     * @return a transaction instance with Id populated
     */
    Transaction buy(BigDecimal amount, Long customerId, Long sellerId);

}
