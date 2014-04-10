package com.appedia.bassat.persistence;

import com.appedia.bassat.domain.Statement;
import com.appedia.bassat.domain.Transaction;

/**
 *
 * @author Muz Omar
 */
public interface StatementMapper {

    /**
     *
     * @param statement
     */
    void insertStatement(Statement statement);

}
