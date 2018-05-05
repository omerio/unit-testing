package com.omerio.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.omerio.model.Transaction;

/**
 * @author omerio
 *
 */
@Repository
@Transactional
public class TransactionDaoImpl implements TransactionDao {

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * Save a transaction
     * @param txn - the transaction to save 
     * @return - the saved transaction
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public Transaction save(Transaction txn) {
        if (txn != null)   {

            if (txn.getId() == null)   {
                // new
                entityManager.persist(txn);
            }   else    {
                // update
                txn = entityManager.merge(txn);
            }
        }

        return txn;
    }

}
