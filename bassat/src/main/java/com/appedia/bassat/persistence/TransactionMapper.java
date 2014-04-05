package com.appedia.bassat.persistence;

import com.appedia.bassat.domain.Transaction;

/**
 *
 * @author Muz Omar
 */
public interface TransactionMapper {

    /**
     *
     * @param transaction
     */
    void insertTransaction(Transaction transaction);

}
