package com.omerio.dao;

import com.omerio.model.Transaction;

/**
 * @author omerio
 *
 */
public interface TransactionDao {

    Transaction save(Transaction txn);

}
