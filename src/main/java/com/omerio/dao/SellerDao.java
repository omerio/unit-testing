package com.omerio.dao;

import com.omerio.model.Seller;

/**
 * @author omerio
 *
 */
public interface SellerDao {

    /**
     * Find a particular Seller by id
     * @param sellerId - the id of the seller
     * @return - the seller if found otherwise null
     */
    Seller findById(Long sellerId);

}
